package cl.thisisalexis.common.core.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * An abstract representation of a rest service call. The main idea is that every service meant to call an external REST
 * service be a concrete implementation of this class, si it has an centralized and common way to access RESTful services
 *
 * @param <Q> represents type returned by the webservice
 * @param <S> represents type received in request to be sent in the body of it
 * @author Alexis Bravo
 */
public abstract class AbstractRestService<Q, S> {

    private RestTemplate restTemplate;
    private String uri;
    private String tempUri;

    protected Q get(S request, HttpHeaders httpHeaders, Map<String, String> pathParams, Map<String, String> queryParams) {
        buildUrl(pathParams, queryParams);
        HttpEntity<S> httpEntity = new HttpEntity(request, httpHeaders);
        ResponseEntity<Q> responseEntity = getRestTemplate().exchange(this.getTempUri(), HttpMethod.GET, httpEntity, getClassType());
        return responseEntity.getBody();
    }

    protected abstract Class<Q> getClassType();

    public RestTemplate getRestTemplate() {
        if (this.restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }

    private void buildUrl(Map<String, String> pathParams, Map<String, String> queryParams) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(this.uri);
        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        this.tempUri = uriComponentsBuilder.build().toString();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTempUri() {
        return tempUri;
    }
}

