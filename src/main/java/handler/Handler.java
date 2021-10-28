package handler;

import server.HTTPRequest;
import server.HTTPResponse;

/**
 * Handler Interface
 * will be implemented by various Application server for different API
 * @author nilimajha
 */
public interface Handler {
    public HTTPResponse handle (HTTPRequest httpRequest);
}