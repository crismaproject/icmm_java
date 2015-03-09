/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper;

import eu.crismaproject.icmm.icmmhelper.entity.BaseEntity;
import eu.crismaproject.icmm.icmmhelper.entity.GenericCollectionResource;
import eu.crismaproject.icmm.icmmhelper.entity.Transition;
import eu.crismaproject.icmm.icmmhelper.entity.Worldstate;
import eu.crismaproject.icmm.icmmhelper.entity.WorldstateCollectionResource;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.1
 */
public final class ICMMClient {

    //~ Instance fields --------------------------------------------------------

    private final transient Client client;
    private final transient WebTarget webTarget;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ICMMClient object.
     *
     * @param  apiurl  DOCUMENT ME!
     */
    public ICMMClient(final String apiurl) {
        final ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new JacksonFeature());
        clientConfig.register(ISODateJacksonObjectMapperProvider.class);

        client = ClientBuilder.newClient(clientConfig);
        webTarget = client.target(apiurl);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<Worldstate> getWorldstates() {
        return getWorldstates(0, true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   level  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<Worldstate> getWorldstates(final int level) {
        return getWorldstates(level, true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   level        DOCUMENT ME!
     * @param   deduplicate  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<Worldstate> getWorldstates(final int level, final boolean deduplicate) {
        final WebTarget target =
            webTarget.path("CRISMA.worldstates")     // NOI18N
            .queryParam("level", level)              // NOI18N
            .queryParam("deduplicate", deduplicate); // NOI18N
        final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
        final Response response = builder.get();
        final WorldstateCollectionResource cr = response.readEntity(WorldstateCollectionResource.class);

        return cr.get$collection();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Worldstate getWorldstate(final int id) {
        return getWorldstate(id, 1, true, true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id     DOCUMENT ME!
     * @param   level  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Worldstate getWorldstate(final int id, final int level) {
        return getWorldstate(id, level, true, true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id           DOCUMENT ME!
     * @param   level        DOCUMENT ME!
     * @param   deduplicate  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Worldstate getWorldstate(final int id, final int level, final boolean deduplicate) {
        return getWorldstate(id, level, deduplicate, true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id              DOCUMENT ME!
     * @param   level           DOCUMENT ME!
     * @param   deduplicate     DOCUMENT ME!
     * @param   omitNullValues  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Worldstate getWorldstate(final int id,
            final int level,
            final boolean deduplicate,
            final boolean omitNullValues) {
        final WebTarget target =
            webTarget.path("CRISMA.worldstates/" + id) // NOI18N
            .queryParam("level", level)                // NOI18N
            .queryParam("deduplicate", deduplicate);   // NOI18N
        final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
        final Response response = builder.get();

        return response.readEntity(Worldstate.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   entity  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getNextId(final String entity) {
        final WebTarget target =
            webTarget.path("CRISMA." + getEntityName(entity))                                          // NOI18N
            .queryParam("level", 1)                                                                    // NOI18N
            .queryParam("limit", 99999999).queryParam("deduplicate", true).queryParam("fields", "id"); // NOI18N
        final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
        final Response response = builder.get();
        final GenericCollectionResource cr = response.readEntity(GenericCollectionResource.class);
        final List<Map> be = cr.get$collection();

        int maxId = -1;
        for (final Map res : be) {
            final int id = Integer.parseInt(String.valueOf(res.get("id")));
            if (maxId < id) {
                maxId = id;
            }
        }

        return maxId + 1;
    }

    /**
     * Creates a new self reference for a new entity identified by the given parameter. Uses
     * {@link #getNextId(java.lang.String)} to produce the new ref. Thus this ref can be used for any newly create
     * object that shall be pushed to the ICMM.
     *
     * @param   <T>     DOCUMENT ME!
     * @param   entity  DOCUMENT ME!
     *
     * @return  the given entity with the $self reference and id set
     */
    public <T extends BaseEntity> T insertSelfRefAndId(final T entity) {
        final String rawName = getEntityName(entity.getEntityName());
        final int id = getNextId(rawName);

        entity.set$self("/CRISMA." + rawName + "/" + id); // NOI18N
        entity.setId(id);

        return entity;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   t  DOCUMENT ME!
     *
     * @throws  RuntimeException  DOCUMENT ME!
     */
    public void putTransition(final Transition t) {
        validateRef(t);

        final WebTarget target =
            webTarget.path("CRISMA.transitions/" + t.getId()) // NOI18N
            .queryParam("requestResultingInstance", false);   // NOI18N
        final Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
        final Response response = builder.put(Entity.json(t));

        if ((response.getStatus() < 200) || (response.getStatus() > 210)) {
            throw new RuntimeException("could not put transition: " + response); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   entity  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private void validateRef(final BaseEntity entity) {
        if (entity.get$self() == null) {
            throw new IllegalStateException("entity must have a $self reference: " + entity);          // NOI18N
        } else if (entity.get$ref() != null) {
            throw new IllegalStateException("entity must not have a $ref if $self is set: " + entity); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getEntityName(final String value) {
        // first dot is domain separator
        final int index = value.indexOf('.');
        if (index >= 0) {
            return value.substring(index + 1);
        } else {
            return value;
        }
    }
}
