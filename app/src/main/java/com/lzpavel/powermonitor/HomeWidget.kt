package com.lzpavel.powermonitor

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import java.time.format.TextStyle

object HomeWidget: GlanceAppWidget() {

    //val countKey = intPreferencesKey("count")

    @Composable
    fun Content() {
        //val count = currentState(key = countKey) ?: 0
        Column (
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .clickable(onClick = actionRunCallback(QueryActionCallback::class.java)),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(text = "V:")
            Text(text = "A:")
            Text(text = "C:")

            /*Text (
                text = count.toString(),
                style = androidx.glance.text.TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = ColorProvider(Color.White),
                    fontSize = 26.sp
                )
            )
            Button(
                text = "Inc",
                onClick = actionRunCallback(IncrementActionCallback::class.java)
            )*/
        }
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

}

class HomeWidgetReceiver() : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = HomeWidget
}

object QueryActionCallback: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Toast.makeText(context, "Widget clicked", Toast.LENGTH_SHORT).show()
        /*updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[CounterWidget.countKey]
            if (currentCount != null) {
                prefs[CounterWidget.countKey] = currentCount + 1
            } else {
                prefs[CounterWidget.countKey] = 1
            }
            CounterWidget.update(context, glanceId)
        }*/
    }
}