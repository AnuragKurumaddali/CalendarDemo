package com.raj.calendardemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.LocalDate;

import java.util.LinkedHashMap;
import java.util.Vector;

public class MainActivity extends Activity {

    private MaterialCalendarView calendarView;

    private Vector<EventDO> vecEvents;
    private CurrentMonthDecorator currentMonthDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(MainActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });*/

        initControls();
    }

    private void initControls(){
        calendarView = findViewById(R.id.calendarView);

        CalendarDay today = CalendarDay.today();


        //set All Events on Open of Calendar
        setSelectedDays();

        // To Increase TextSize
        increaseTextSize(true);

        //To Show Next Month Dates
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        //To List Out All Calendar Events
        final LinkedHashMap<CalendarDay,Vector<EventDO>> hashMapEvents = getMultipleEventsHashMap();
//        Vector<MultiEventsDO> vecMultipleEvents = getMultipleEvents();
//        Vector<CalendarDay> vecCalendarDayEvents = vecMultipleEvents.
//        Vector<CalendarDay> vecCalendarDayEvents = createCalendarEventDays();

        currentMonthDecorator = new CurrentMonthDecorator(today.getMonth());

        //To Add Calendar Events with Dots
        calendarView.addDecorators(
                currentMonthDecorator,
                new MySelectorDecorator(this),
                new EventDecorator(Color.GREEN,hashMapEvents.keySet()),
                new CurrentDateDecorator(this,today));

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                Log.e("aaa","Date : "+date.getDate());

                Vector<EventDO> vecEvents = hashMapEvents.get(date);
                String str = "";
                if(vecEvents != null && vecEvents.size()>0){
                    str = "";
                    for(EventDO eventDO:vecEvents){
                        str += eventDO.eventTitle +"\n"+eventDO.eventDescription+"\n";
                    }
                }
                else
                    str = "";

                if(!str.isEmpty())
                    Toast.makeText(MainActivity.this,""+str,Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.setOnDateLongClickListener(new OnDateLongClickListener() {
            @Override
            public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
//                calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

//                calendarView.setDateSelected(date,true);
            }
        });

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                widget.removeDecorator(currentMonthDecorator);
                currentMonthDecorator = new CurrentMonthDecorator(date.getMonth());
                widget.addDecorator(currentMonthDecorator);
                Log.e("aaa","Month Changed to = "+date.getMonth()+"\n date = "+date);
            }
        });

    }

    private void setSelectedDays(){
        CalendarDay cD = CalendarDay.from(2018, 5, 2);
        calendarView.setDateSelected(cD,true);
    }

    private void increaseTextSize(boolean isLargeRequired){
        if (isLargeRequired) {
            calendarView.setHeaderTextAppearance(R.style.CustomTextAppearance_Header);
            calendarView.setDateTextAppearance(R.style.CustomTextAppearance);
            calendarView.setWeekDayTextAppearance(R.style.CustomTextAppearance);
        } else {
            calendarView.setHeaderTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Header);
            calendarView.setDateTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_Date);
            calendarView.setWeekDayTextAppearance(R.style.TextAppearance_MaterialCalendarWidget_WeekDay);
        }
    }

    private Vector<CalendarDay> createCalendarEventDays(){
        Vector<CalendarDay> vecCalendarDays = new Vector<>();
        LocalDate localDate = LocalDate.now().minusMonths(2);
        for(int i =0;i<30;i++){
            CalendarDay calendarDay = CalendarDay.from(localDate);
            vecCalendarDays.add(calendarDay);
            localDate = localDate.plusDays(5);
        }
        return vecCalendarDays;
    }

    private Vector<MultiEventsDO> getMultipleEvents(){
        Vector<MultiEventsDO> vecMultipleEvents = new Vector<>();
        MultiEventsDO multiEventsDO = new MultiEventsDO();
        LocalDate localDate = LocalDate.of(2018,11,2);
        multiEventsDO.calendarDay = CalendarDay.from(localDate);
        EventDO eventDO = new EventDO();
        eventDO.eventDescription = "Have a blast party from Kishore.";
        eventDO.eventTime = "10:00:00";
        eventDO.eventTitle = "Kishore Birthday";
        multiEventsDO.eventDO = eventDO;
        vecMultipleEvents.add(multiEventsDO);
        MultiEventsDO multiEventsDO1 = new MultiEventsDO();
        LocalDate localDate1 = LocalDate.of(2018,12,10);
        multiEventsDO1.calendarDay = CalendarDay.from(localDate1);
        EventDO eventDO1 = new EventDO();
        eventDO1.eventDescription = "Have a blast party from Satya.";
        eventDO1.eventTime = "10:00:00";
        eventDO1.eventTitle = "Satya Birthday";
        multiEventsDO1.eventDO = eventDO1;
        vecMultipleEvents.add(multiEventsDO1);
        return vecMultipleEvents;
    }
    private LinkedHashMap<CalendarDay,Vector<EventDO>> getMultipleEventsHashMap(){
        LinkedHashMap<CalendarDay,Vector<EventDO>> hashMapEvents = new LinkedHashMap<>();
        Vector<EventDO> vecMultipleEvents = new Vector<>();
        LocalDate localDate = LocalDate.of(2018,11,2);
        CalendarDay calendarDay = CalendarDay.from(localDate);
        EventDO eventDO = new EventDO();
        eventDO.eventDescription = "Have a blast party from Kishore.";
        eventDO.eventTime = "10:00:00";
        eventDO.eventTitle = "Kishore Birthday";
        vecMultipleEvents.add(eventDO);
        EventDO eventDO2 = new EventDO();
        eventDO2.eventDescription = "Kishore resort Party,Full Enjoying";
        eventDO2.eventTime = "10:00:00";
        eventDO2.eventTitle = "Kishore Resort Party";
        vecMultipleEvents.add(eventDO2);
        hashMapEvents.put(calendarDay,vecMultipleEvents);
        Vector<EventDO> vecMultipleEvents1 = new Vector<>();
        LocalDate localDate1 = LocalDate.of(2018,12,10);
        CalendarDay calendarDay1 = CalendarDay.from(localDate1);
        EventDO eventDO1 = new EventDO();
        eventDO1.eventDescription = "Have a blast party from Satya.";
        eventDO1.eventTime = "10:00:00";
        eventDO1.eventTitle = "Satya Birthday";
        vecMultipleEvents1.add(eventDO1);
        hashMapEvents.put(calendarDay1,vecMultipleEvents1);
        return hashMapEvents;
    }

}
