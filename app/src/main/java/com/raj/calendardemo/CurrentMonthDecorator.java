package com.raj.calendardemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Use a custom selector
 */
public class CurrentMonthDecorator implements DayViewDecorator {

  private int currentMonth;
  private static final int color = Color.parseColor("#D81B60");

  public CurrentMonthDecorator(int currentMonth) {
    this.currentMonth = currentMonth;
  }

  @Override
  public boolean shouldDecorate(CalendarDay day) {
    return day.getMonth() != currentMonth;
  }

  @Override
  public void decorate(DayViewFacade view) {
    view.addSpan(new ForegroundColorSpan(Color.BLUE));
  }
}
