package observation;

import java.util.List;
public interface Observee<T> {

    public List<AbstractObserver<T>> getObservers();

    public void addObserver(AbstractObserver<T> observer);

    public void notify(T message);

    public void removeObserver(AbstractObserver<T> observer);


}