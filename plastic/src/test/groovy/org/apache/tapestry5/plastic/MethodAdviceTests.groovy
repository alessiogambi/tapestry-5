package org.apache.tapestry5.plastic

import java.sql.SQLException
import org.apache.tapestry5.plastic.test.NoopAdvice
import testannotations.FieldAnnotation
import testannotations.Maybe
import testannotations.MethodAnnotation
import testannotations.Truth
import testinterfaces.MagicContainer

class MethodAdviceTests extends AbstractPlasticSpecification
{
    def "advice for a void method"()
    {
        setup:

        def didInvoke = false
        def methodId;

        def mgr = createMgr({ PlasticClass pc ->

            def method = findMethod(pc, "aSingleMethod")

            methodId = method.methodIdentifier

            findMethod(pc, "aSingleMethod").addAdvice({
                didInvoke = true

                assert it.method.name == "aSingleMethod"

                assert it.getParameter(0) == 123

                assert it.hasAnnotation(Deprecated.class) == false
                assert it.hasAnnotation(Maybe.class) == true

                assert it.getAnnotation(Maybe.class).value() == Truth.YES

                it.proceed()
            } as MethodAdvice)
        } as PlasticClassTransformer)

        when:

        def o = mgr.getClassInstantiator("testsubjects.SingleMethod").newInstance()

        then:

        didInvoke == false

        methodId == "testsubjects.SingleMethod.aSingleMethod(int)"

        when:

        o.aSingleMethod(123)

        then:

        didInvoke == true
    }

    def "multiple advice on method with parameters and return values"()
    {

        setup:

        def mgr = createMgr({ PlasticClass pc ->
            findMethod(pc, "dupe").addAdvice({

                it.setParameter(0, it.getParameter(0) + 2)
                it.proceed()
            } as MethodAdvice).addAdvice({

                it.setParameter(0, it.getParameter(0) * 3)
                it.proceed()

                it.setReturnValue(it.getReturnValue().toUpperCase())
            } as MethodAdvice)
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.MethodAdviceTarget").newInstance()

        expect:

        o.dupe(2, "Fam") == "FAM FAM FAM FAM FAM FAM FAM FAM FAM FAM FAM FAM"
    }

    def "method that throws exceptions"()
    {

        setup:

        def mgr = createMgr({ PlasticClass pc ->
            findMethod(pc, "maybeThrow").addAdvice(new NoopAdvice())
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.MethodAdviceTarget").newInstance()

        expect:

        o.maybeThrow(7L) == 7L

        when:

        o.maybeThrow(0L)

        then:

        thrown(SQLException)
    }

    def "setting return value clears checked exceptions"()
    {
        def mgr = createMgr({ PlasticClass pc ->
            findMethod(pc, "maybeThrow").addAdvice({  MethodInvocation mi ->

                mi.proceed()

                if (mi.didThrowCheckedException())
                {
                    mi.setReturnValue(-1L)
                }
            } as MethodAdvice)
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.MethodAdviceTarget").newInstance()

        expect:

        o.maybeThrow(9L) == 9L

        o.maybeThrow(0L) == -1L
    }

    /**
     * This is important because each double/long takes up two local variable slots.
     *
     * @return
     */
    def "method with long and double parameters"()
    {
        setup:

        def mgr = createMgr({ PlasticClass pc ->
            findMethod(pc, "doMath").addAdvice(new NoopAdvice())
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.WidePrimitives").newInstance()

        expect:
        "The interceptor builds proper bytecode to pass the values through"

        o.doMath(2l, 4.0d, 5, 6l) == 38d
    }

    def "method advice does not interfere with field instrumentation (advice first)"()
    {
        FieldConduit fc = [get: { instance, context ->
            return "via conduit"
        }, set: { instance, context -> }] as FieldConduit

        MethodAdvice justProceed = { inv -> inv.proceed() } as MethodAdvice

        def mgr = createMgr({ PlasticClass pc ->

            pc.getMethodsWithAnnotation(MethodAnnotation.class).each({ m ->
                m.addAdvice(justProceed)
            })

            pc.getFieldsWithAnnotation(FieldAnnotation.class).each({ f ->
                f.setConduit(fc)
            })
        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.FieldConduitInsideAdvisedMethod").newInstance()

        expect:

        o.magic == "via conduit"
    }

    def "method advice does not interfere with field instrumentation (conduit first)"()
    {
        FieldConduit fc = [get: { instance, context ->
            return "via conduit"
        }, set: { instance, context -> }] as FieldConduit

        MethodAdvice justProceed = { inv -> inv.proceed() } as MethodAdvice

        def mgr = createMgr({ PlasticClass pc ->

            pc.getFieldsWithAnnotation(FieldAnnotation.class).each({ f ->
                f.setConduit(fc)
            })

            pc.getMethodsWithAnnotation(MethodAnnotation.class).each({ m ->
                m.addAdvice(justProceed)
            })

        } as PlasticClassTransformer)

        def o = mgr.getClassInstantiator("testsubjects.FieldConduitInsideAdvisedMethod").newInstance()

        expect:

        o.magic == "via conduit"
    }

    def "method advice does not interfere with field instrumentation (instance context version)"()
    {
        MagicContainer container = Mock()

        FieldConduit fc = [get: { instance, context ->

            return context.get(MagicContainer.class).magic()

        }, set: { instance, context -> }] as FieldConduit

        MethodAdvice justProceed = { inv -> inv.proceed() } as MethodAdvice

        def mgr = createMgr({ PlasticClass pc ->

            pc.getMethodsWithAnnotation(MethodAnnotation.class).each({ m ->
                m.addAdvice(justProceed)
            })

            pc.getFieldsWithAnnotation(FieldAnnotation.class).each({ f ->
                f.setConduit(fc)
            })
        } as PlasticClassTransformer)

        if (false) { enableBytecodeDebugging(mgr) }

        def o = mgr.getClassInstantiator("testsubjects.FieldConduitInsideAdvisedMethod").with(MagicContainer.class, container).newInstance()

        when:

        o.magic == "via context and mock"

        then:

        1 * container.magic() >> "via context and mock"
    }

    def "method advice on method that accesses a field with a conduit (more complex structure)"()
    {
        MagicContainer container = Mock()

        FieldConduit fc = [get: { instance, context ->

            return context.get(MagicContainer.class)

        }, set: { instance, context -> }] as FieldConduit

        MethodAdvice justProceed = { inv -> inv.proceed() } as MethodAdvice

        def mgr = createMgr({ PlasticClass pc ->

            pc.getMethodsWithAnnotation(MethodAnnotation.class).each({ m ->
                m.addAdvice(justProceed)
            })

            pc.getFieldsWithAnnotation(FieldAnnotation.class).each({ f ->
                f.setConduit(fc)
            })
        } as PlasticClassTransformer)

        if (false) { enableBytecodeDebugging(mgr) }

        def o = mgr.getClassInstantiator("testsubjects.FieldConduitAdvisedMethodComplexCase").with(MagicContainer.class, container).newInstance()

        when:

        o.magic == "via context"

        then:

        1 * container.magic() >> "via context"

    }

}
