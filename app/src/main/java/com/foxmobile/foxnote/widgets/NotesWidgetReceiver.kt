package com.foxmobile.foxnote.widgets

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.foxmobile.foxnote.database.Database
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotesWidgetReceiver: GlanceAppWidgetReceiver()  {

    override val glanceAppWidget: GlanceAppWidget = NotesWidget()
}