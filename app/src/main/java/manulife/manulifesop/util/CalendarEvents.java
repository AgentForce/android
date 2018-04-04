package manulife.manulifesop.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

public class CalendarEvents {
    public static long pushEventToLocalCalendar(Context context, int eventID,String title, String addInfo, String place, int status, long startDate, long endDate, boolean isAllDay, boolean needReminder, int timeRemind) {
        /***************** Event: note(without alert) *******************/

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title+"_"+eventID);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);

        //endDate = startDate + 1000 * 60 * 60;
        eventValues.put("dtstart", startDate);
        eventValues.put("dtend", endDate);

        if (isAllDay)
            eventValues.put("allDay", 1);//If it is bithday alarm or such
        else
            eventValues.put("allDay", 0);//If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):
        eventValues.put("eventTimezone", "UTC/GMT +7:00");
        //eventValues.put("eventEndTimezone", "UTC/GMT +2:00");
        /*Comment below visibility and transparency  column to avoid java.lang.IllegalArgumentException column visibility is invalid error */

        /*eventValues.put("visibility", 3); // visibility to default (0),
                                            // confidential (1), private
                                            // (2), or public (3):
        eventValues.put("transparency", 0); // You can control whether
                                            // an event consumes time
                                            // opaque (0) or transparent
                                            // (1).
        */
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

        //Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        //Uri eventUri = context.getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        context.getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        //long remindID = Long.parseLong(eventUri.getLastPathSegment());

        if (needReminder) {
            //***************** Event: Reminder(with alert) Adding reminder to event *******************//

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", timeRemind); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            //Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
            //Uri reminderUri = context.getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
            context.getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }
        return eventID;
    }
    public static void deleteEvent(Context context,int eventIDAPI,String title)
    {
        //get id from event title
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://com.android.calendar/events"),
                new String[] { "_id","title"}, " title = ? ",
                new String[] { title+"_"+eventIDAPI }, null);

        if (cursor.moveToFirst()) {
            //Yes Event Exist...
            //delete event
            ContentResolver cr = context.getContentResolver();
            Uri eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, cursor.getLong(0));
            ContentValues event = new ContentValues();
            event.put(CalendarContract.Events.DELETED, 1);
            cr.delete(eventUri,null, null);
        }
    }

    public static void updateEvent(Context context, int eventID,String titleOld,String titleNew, String addInfo, String place, int status, long startDate, long endDate, boolean isAllDay, boolean needReminder, int timeRemind){
        //get id from event title
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://com.android.calendar/events"),
                new String[] { "_id","title"}, " title = ? ",
                new String[] { titleOld+"_"+eventID }, null);

        if (cursor.moveToFirst()){
            //update event
            Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, cursor.getLong(0));
            ContentValues values = new ContentValues();
            values.put("title",titleNew+"_"+eventID);
            values.put("description",addInfo);
            values.put("eventLocation",place);
            values.put("dtstart", startDate);
            values.put("dtend", endDate);

            if (isAllDay)
                values.put("allDay", 1);//If it is bithday alarm or such
            else
                values.put("allDay", 0);//If it is bithday alarm or such
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):

            context.getContentResolver().update(updateUri, values, null, null);

            if (needReminder) {
                //***************** Event: Reminder(with alert) Adding reminder to event *******************//
                Uri updateRemindUri = ContentUris.withAppendedId(CalendarContract.Reminders.CONTENT_URI, eventID);

                ContentValues reminderValues = new ContentValues();
                reminderValues.put("minutes", timeRemind); // Default value of the
                reminderValues.put("method", 1); // Alert Methods: Default(0),
                // Alert(1), Email(2),                // SMS(3)
                context.getContentResolver().update(updateRemindUri, reminderValues,null,null);
            }
        }
    }
    public static boolean isEventInCal(Context context,int eventIDAPI, String eventTitle) {

        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://com.android.calendar/events"),
                new String[] { "_id","title"}, " title = ? ",
                new String[] { eventTitle+"_"+eventIDAPI }, null);

        if (cursor.moveToFirst()) {
            //Yes Event Exist...
            Log.d("test", "id event "+cursor.getString(0));
            Log.d("test","title evnet "+ cursor.getString(1));
            return true;
        }
        return false;
    }
}
