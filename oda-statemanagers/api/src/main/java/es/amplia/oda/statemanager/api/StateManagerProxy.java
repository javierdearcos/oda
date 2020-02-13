package es.amplia.oda.statemanager.api;

import es.amplia.oda.core.commons.osgi.proxies.OsgiServiceProxy;
import es.amplia.oda.core.commons.utils.DatastreamValue;
import es.amplia.oda.core.commons.utils.DevicePattern;
import es.amplia.oda.event.api.Event;

import org.osgi.framework.BundleContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class StateManagerProxy implements StateManager, AutoCloseable {

    private final OsgiServiceProxy<StateManager> proxy;

    public StateManagerProxy(BundleContext bundleContext) {
        this.proxy = new OsgiServiceProxy<>(StateManager.class, bundleContext);
    }

    @Override
    public CompletableFuture<List<DatastreamValue>> getDatastreamInformation(String deviceId, String datastreamId) {
        return proxy.callFirst(stateManager -> stateManager.getDatastreamInformation(deviceId, datastreamId));
    }

    @Override
    public CompletableFuture<Set<List<DatastreamValue>>> getDatastreamsInformation(String deviceId, Set<String> datastreamIds) {
        return proxy.callFirst(stateManager -> stateManager.getDatastreamsInformation(deviceId, datastreamIds));
    }

    @Override
    public CompletableFuture<Set<List<DatastreamValue>>> getDatastreamsInformation(DevicePattern devicePattern, String datastreamId) {
        return proxy.callFirst(stateManager -> stateManager.getDatastreamsInformation(devicePattern, datastreamId));
    }

    @Override
    public CompletableFuture<Set<List<DatastreamValue>>> getDatastreamsInformation(DevicePattern devicePattern, Set<String> datastreamIds) {
        return proxy.callFirst(stateManager -> stateManager.getDatastreamsInformation(devicePattern, datastreamIds));
    }

    @Override
    public CompletableFuture<Set<List<DatastreamValue>>> getDeviceInformation(String deviceId) {
        return proxy.callFirst(stateManager -> stateManager.getDeviceInformation(deviceId));
    }

    @Override
    public CompletableFuture<List<DatastreamValue>> refreshDatastreamValue(String deviceId, String datastreamId, Object value) {
        return proxy.callFirst(stateManager -> stateManager.refreshDatastreamValue(deviceId, datastreamId, value));
    }

    @Override
    public CompletableFuture<Set<List<DatastreamValue>>> refreshDatastreamValues(String deviceId, Map<String, Object> datastreamValues) {
        return proxy.callFirst(stateManager -> stateManager.refreshDatastreamValues(deviceId, datastreamValues));
    }

    @Override
    public void registerToEvents(EventHandler eventHandler) {
        proxy.consumeFirst(stateManager -> stateManager.registerToEvents(eventHandler));
    }

    @Override
    public void unregisterToEvents(EventHandler eventHandler) {
        proxy.consumeFirst(stateManager -> stateManager.unregisterToEvents(eventHandler));
    }

    @Override
    public void onReceivedEvent(Event event) {
        proxy.consumeFirst(stateManager -> stateManager.onReceivedEvent(event));
    }

    @Override
    public void close() {
        proxy.close();
    }
}
