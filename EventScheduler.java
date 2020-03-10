import java.util.*;

/*
EventScheduler: ideally our way of controlling what happens in our virtual world
 */

final class EventScheduler {
   //variables
   private PriorityQueue<Event> eventQueue;
   private Map<Entity, List<Event>> pendingEvents;
   private double timeScale;

   public EventScheduler(double timeScale) {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }

   public void scheduleEvent(Entity entity, Action action, long afterPeriod) {
      long time = System.currentTimeMillis() +
              (long) (afterPeriod * timeScale);
      Event event = new Event(action, time, entity);

      eventQueue.add(event);

      // update list of pending events for the given entity
      List<Event> pending = pendingEvents.getOrDefault(entity,
              new LinkedList<>());
      pending.add(event);
      pendingEvents.put(entity, pending);
   }

   public void unscheduleAllEvents(Entity entity) {
      List<Event> pending = pendingEvents.remove(entity);

      if (pending != null) {
         for (Event event : pending) {
            eventQueue.remove(event);
         }
      }
   }

   private void removePendingEvent(Event event) {
      List<Event> pending = pendingEvents.get(event.getEntity());

      if (pending != null) {
         pending.remove(event);
      }
   }

   public void updateOnTime(long time) {
      while (!eventQueue.isEmpty() &&
              eventQueue.peek().getTime() < time) {
         Event next = eventQueue.poll();

         removePendingEvent(next);

         next.getAction().executeAction(this);
      }
   }

   public static boolean adjacent(Point p1, Point p2)
   {
      return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) ||
              (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
   }
}