package com.example.aksha.gjusteve.fragments.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aksha.gjusteve.R;
import com.example.aksha.gjusteve.helper.CalanderDecorator.CurrentDateDecorator;
import com.example.aksha.gjusteve.helper.CalanderDecorator.EventDecorator;
import com.example.aksha.gjusteve.helper.CalanderDecorator.HighlightWeekendsDecorator;
import com.example.aksha.gjusteve.helper.CalanderDecorator.MySelectorDecorator;
import com.example.aksha.gjusteve.helper.CalanderDecorator.OneDayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CalendarActivity extends Fragment {

    final private List<CalendarDay> gazettedHolidays = new ArrayList<>();
    final private List<CalendarDay> restrictedHolidays = new ArrayList<>();
    final private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    final private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private String[] holidays;
    private View mView;
    private TextView viewTitle;

    private List<CalendarDay> getGazettedHolidays() {
        try {
            String[] response = new String[]{"2017-01-05",
                    "2017-01-26",
                    "2017-02-01",
                    "2017-02-10",
                    "2017-02-21",
                    "2017-02-24",
                    "2017-03-13",
                    "2017-03-23",
                    "2017-04-04",
                    "2017-04-09",
                    "2017-04-13",
                    "2017-04-14",
                    "2017-04-28",
                    "2017-05-10",
                    "2017-05-28",
                    "2017-06-09",
                    "2017-06-26",
                    "2017-07-26",
                    "2017-08-15",
                    "2017-09-02",
                    "2017-09-21",
                    "2017-09-23",
                    "2017-09-30",
                    "2017-10-02",
                    "2017-10-05",
                    "2017-10-19",
                    "2017-11-01",
                    "2017-11-04",
                    "2017-12-25",
                    "2017-12-26"};
            holidays = response;
            for (String StartDate : response) {

                Date date = simpleDateFormat.parse(StartDate);
                CalendarDay day = CalendarDay.from(date);
                gazettedHolidays.add(day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gazettedHolidays;
    }

    private List<CalendarDay> getRestrictedHolidays() {
        try {
            String[] response = new String[]{
                    "2017-06-16",
                    "2017-07-31",
                    "2017-08-07",
                    "2017-10-26",
                    "2017-11-24"};
            for (String StartDate : response) {
                Date date = simpleDateFormat.parse(StartDate);
                CalendarDay day = CalendarDay.from(date);
                restrictedHolidays.add(day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restrictedHolidays;
    }

    public CalendarActivity() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_calendar, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private String displayEvent(CalendarDay date) {
        int count = 0;
        String response = DateFormat.getDateInstance().format(new Date());

        String[] eventTitle = new String[]{"Guru Gobind Singh Birthday",
                "Republic Day",
                "S.C.R.Jayanti & B.Panchmi",
                "Guru Ravidass Jayanti",
                "Maharishi Dayanand",
                "Maha Shivratri",
                "Holi",
                "Shaheedi Divas of B.S.R.",
                "Ram Navmi",
                "Mahavir Jatanti",
                "Baisakhi",
                "Dr.B.R.A Jayanti",
                "Lord Parshu Ram Jayanti",
                "Budh Purnima",
                "Maharana Partap Jayanti",
                "Sant Kabir Jayanti",
                "Id- ul- Fitr",
                "Hariyali Teej",
                "Independence Day/ Janmashtami",
                "Id-ul-Juha (Bakrid)",
                "Maharaja Agrasen Jayanti",
                "Haryana Heroes Day",
                "Dussehra",
                "Mahatama Gandhi B,Day",
                "Maharishi Balmiki's",
                "Diwali",
                "Haryana Day",
                "Guru Nanak's Birthday",
                "Christmas Day",
                "S.Udham Singh B'Day"};
        try {
            for (String todayEvent : holidays) {
                Date fetchedDate = simpleDateFormat.parse(todayEvent);
                CalendarDay day = CalendarDay.from(fetchedDate);
                if (day.equals(date)) {
                    response = eventTitle[count];
                    break;
                }
                count = count + 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void onStart() {
        super.onStart();
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                MaterialCalendarView materialCalendarView = mView.findViewById(R.id.calendarView);
                materialCalendarView.addDecorators(
                        new HighlightWeekendsDecorator(Color.parseColor("#228BC34A")),
                        new CurrentDateDecorator(), new EventDecorator(Color.RED, getGazettedHolidays()),
                        new EventDecorator(Color.BLUE, getRestrictedHolidays()),
                        new MySelectorDecorator(Objects.requireNonNull(getActivity())), oneDayDecorator);
                CalendarDay todayDate = CalendarDay.today();
                viewTitle = mView.findViewById(R.id.todaysEvents);
                viewTitle.setText(displayEvent(todayDate));
                materialCalendarView.setOnDateChangedListener((widget, selectedDate, selected) -> {
                    oneDayDecorator.setDate(selectedDate.getDate());
                    widget.invalidateDecorators();
                    viewTitle.setText(displayEvent(selectedDate));
                });
            }
        };
        thread.run();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
