package com.lzpavel.powermonitor

import android.content.Context
import android.util.Log
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
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider


object HomeWidget: GlanceAppWidget() {

    @Composable
    fun Content(
        voltage: String? = null,
        current: String? = null,
        capacity: String? = null,
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .clickable(onClick = actionRunCallback(QueryActionCallback::class.java)),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = "V:${voltage ?: "-"}\n" +
                        "A:${current ?: "-"}\n" +
                        "C:${capacity ?: "-"}",
                modifier = GlanceModifier.clickable(
                    onClick = actionRunCallback(QueryActionCallback::class.java)
                ),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 16.sp
                )
            )

        }
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        var v: String? = null
        var a: String? = null
        var c: String? = null
        val su = SuperUserSimple()
        if (su.open()){
            v = su.readVoltage()
            a = su.readCurrent()
            c = su.readCapacity()
            su.close()
        }
        provideContent {
            Content(v, a, c)
        }
    }

}

class HomeWidgetReceiver() : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = HomeWidget
}

object QueryActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        HomeWidget.update(context, glanceId)
    }
}