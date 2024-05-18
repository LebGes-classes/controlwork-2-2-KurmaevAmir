public class BroadcastsTime implements Comparable<BroadcastsTime>{
    private final byte hour;
    private final byte minute;

    public BroadcastsTime(String time) {
        String[] parts = time.split(":");
        this.hour = Byte.parseByte(parts[0]);
        this.minute = Byte.parseByte(parts[1]);
    }

    public byte getHour() {
        return hour;
    }

    public byte getMinute() {
        return minute;
    }

    public boolean after(BroadcastsTime t) {
        return this.compareTo(t) > 0;
    }

    public boolean before(BroadcastsTime t) {
        return this.compareTo(t) < 0;
    }

    public boolean between(BroadcastsTime t1, BroadcastsTime t2) {
        return this.after(t1) && this.before(t2);
    }

    @Override
    public int compareTo(BroadcastsTime t) {
        if (this.hour != t.hour) {
            return this.hour - t.hour;
        }
        return this.minute - t.minute;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
