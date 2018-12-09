package com.raj.calendardemo;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Use a custom selector
 */
public class CurrentDateDecorator implements DayViewDecorator {

  private final Drawable drawable;
  private CalendarDay calendarDay;

  public CurrentDateDecorator(Activity context,CalendarDay calendarDay) {
    drawable = context.getResources().getDrawable(R.drawable.current_date_selector);
    this.calendarDay = calendarDay;
  }

  @Override
  public boolean shouldDecorate(CalendarDay day) {
    return day.getDate().isEqual(calendarDay.getDate());
  }

  @Override
  public void decorate(DayViewFacade view) {
    view.setSelectionDrawable(drawable);
  }
}
