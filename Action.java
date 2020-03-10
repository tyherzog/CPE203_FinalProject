/*
Action: ideally what our various entities might do in our virtual world
 */

public interface Action {
   public void executeAction(EventScheduler scheduler);
}