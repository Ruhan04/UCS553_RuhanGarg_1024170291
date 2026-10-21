import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

public class Q2 {
    public static void main(String[] args) {
        MediaQueue queue = new MediaQueue(3);

        queue.addLast(new Song("Believer", "Imagine Dragons"));
        queue.addLast(new Song("Faded", "Alan Walker"));
        queue.addLast(new Song("Shape of You", "Ed Sheeran"));
        queue.addLast(new Song("Faded", "Alan Walker"));
        queue.addLast(new Song("Closer", "The Chainsmokers"));
        queue.addLast(new Song("Believer", "Imagine Dragons"));

        System.out.println("Initial queue:");
        queue.printForward();

        System.out.println("\nPriority add to front:");
        queue.addFirst(new Song("Kesariya", "Arijit Singh"));
        queue.printForward();

        System.out.println("\nInsert at position 2:");
        queue.insertAt(2, new Song("Senorita", "Shawn Mendes"));
        queue.printForward();

        System.out.println("\nRemoving duplicates:");
        int removed = queue.removeDuplicates();
        System.out.println("Removed " + removed + " duplicate(s)");
        queue.printForward();

        System.out.println("\nBackward traversal:");
        queue.printBackward();

        System.out.println("\nPlaying songs:");
        queue.playNext();
        queue.playNext();
        queue.playNext();
        queue.playNext();

        System.out.println("\nRecently played (latest first):");
        queue.printHistory();

        System.out.println("\nRemaining queue:");
        queue.printForward();
    }
}

class Song {
    private final String title;
    private final String artist;

    Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song s = (Song) o;
        return title.equalsIgnoreCase(s.title) && artist.equalsIgnoreCase(s.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), artist.toLowerCase());
    }

    @Override
    public String toString() {
        return title + " - " + artist;
    }
}

class MediaQueue {
    private final LinkedList<Song> queue = new LinkedList<>();
    private final LinkedList<Song> history = new LinkedList<>();
    private final int historyLimit;

    MediaQueue(int historyLimit) {
        this.historyLimit = historyLimit;
    }

    void addLast(Song song) {
        queue.addLast(song);
    }

    void addFirst(Song song) {
        queue.addFirst(song);
    }

    void insertAt(int position, Song song) {
        if (position < 0 || position > queue.size()) {
            System.out.println("Invalid position: " + position);
            return;
        }
        ListIterator<Song> it = queue.listIterator(position);
        it.add(song);
    }

    int removeDuplicates() {
        Set<Song> seen = new HashSet<>();
        int removed = 0;
        Iterator<Song> it = queue.iterator();
        while (it.hasNext()) {
            if (!seen.add(it.next())) {
                it.remove();
                removed++;
            }
        }
        return removed;
    }

    Song playNext() {
        Song song = queue.pollFirst();
        if (song == null) {
            System.out.println("Queue is empty");
            return null;
        }
        System.out.println("Now playing: " + song);
        history.addFirst(song);
        if (history.size() > historyLimit) {
            history.removeLast();
        }
        return song;
    }

    void printForward() {
        ListIterator<Song> it = queue.listIterator();
        int i = 1;
        while (it.hasNext()) {
            System.out.println("  " + i++ + ". " + it.next());
        }
    }

    void printBackward() {
        ListIterator<Song> it = queue.listIterator(queue.size());
        int i = queue.size();
        while (it.hasPrevious()) {
            System.out.println("  " + i-- + ". " + it.previous());
        }
    }

    void printHistory() {
        for (Song s : history) {
            System.out.println("  " + s);
        }
    }
}
