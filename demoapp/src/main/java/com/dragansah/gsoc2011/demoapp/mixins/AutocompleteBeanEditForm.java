package com.dragansah.gsoc2011.demoapp.mixins;

import org.apache.tapestry5.annotations.EmbeddedMixin;

public class AutocompleteBeanEditForm
{
    @SuppressWarnings("unused")
    @EmbeddedMixin("editor.propertyeditor")
    private PropertyEditorMixin testMixin;
}
