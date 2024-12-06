package observation;

import java.util.List;

public interface AbstractObserver<T> {
  public List<T> getMessageList();

  public void task(T message);
}
