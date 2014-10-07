package org.laybe.proxymate.android.events;

/**
 * Wrapper for greenrobot EventBus
 */
public class EventBusImpl implements EventBus {

	private final de.greenrobot.event.EventBus bus;
	
	public EventBusImpl(de.greenrobot.event.EventBus bus) {
		this.bus = bus;
	}
	
	@Override
	public void post(Object event) {
		bus.post(event);
	}

	@Override
	public void postSticky(Object event) {
		bus.postSticky(event);
	}

	@Override
	public void register(Object subscriber) {
		bus.register(subscriber);
	}

	@Override
	public void registerSticky(Object subscriber) {
		bus.registerSticky(subscriber);
	}

	@Override
	public void unregister(Object subscriber) {
		bus.unregister(subscriber);
	}

	
}
