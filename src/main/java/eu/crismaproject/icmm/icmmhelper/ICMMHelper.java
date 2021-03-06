/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.crismaproject.icmm.icmmhelper.entity.BaseEntity;
import eu.crismaproject.icmm.icmmhelper.entity.Category;
import eu.crismaproject.icmm.icmmhelper.entity.DataItem;
import eu.crismaproject.icmm.icmmhelper.entity.EntityIdentifier;
import eu.crismaproject.icmm.icmmhelper.entity.Transition;
import eu.crismaproject.icmm.icmmhelper.entity.Transition.Status;
import eu.crismaproject.icmm.icmmhelper.entity.TransitionStatus;
import eu.crismaproject.icmm.icmmhelper.entity.Worldstate;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.2
 */
public final class ICMMHelper {

    //~ Static fields/initializers ---------------------------------------------

    private static final Pattern ENTITY_IDENTIFIER_PATTERN = Pattern.compile("/(\\w+)\\.(\\w+)(/(\\d+))?"); // NOI18N

    private static final ObjectMapper MAPPER = new ObjectMapper(new JsonFactory());

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ICMMHelper object.
     */
    private ICMMHelper() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   entity  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static EntityIdentifier getEntityIdentifier(final BaseEntity entity) {
        final String ref = (entity.get$self() == null) ? entity.get$ref() : entity.get$self();

        final Matcher matcher = ENTITY_IDENTIFIER_PATTERN.matcher(ref);

        if (matcher.matches()) {
            final String domain = matcher.group(1);
            final String entityName = matcher.group(2);
            final Integer id = (matcher.group(4) == null) ? null : Integer.parseInt(matcher.group(4));

            if ((domain == null) || (entityName == null)) {
                throw new IllegalStateException("entity with incorrect ref: " + ref); // NOI18N
            }

            return new EntityIdentifier(domain, entityName, id);
        } else {
            throw new IllegalStateException("entity with incorrect ref: " + ref); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   name         DOCUMENT ME!
     * @param   description  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Transition createTransition(final String name, final String description) {
        final Transition t = new Transition();
        t.setName(name);
        t.setDescription(description);
        t.setTransitionstatuscontenttype(MediaType.APPLICATION_JSON);

        return updateTransition(t, Status.RUNNING, 0, -1, "Initializing"); // NOI18N
    }

    /**
     * DOCUMENT ME!
     *
     * @param   t         DOCUMENT ME!
     * @param   status    DOCUMENT ME!
     * @param   step      DOCUMENT ME!
     * @param   maxStep   DOCUMENT ME!
     * @param   stepName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static Transition updateTransition(final Transition t,
            final Status status,
            final int step,
            final int maxStep,
            final String stepName) {
        final TransitionStatus ts = new TransitionStatus(status.name(), step, maxStep, stepName);

        try {
            final String content = MAPPER.writeValueAsString(ts);
            t.setTransitionstatus(content);
        } catch (final JsonProcessingException ex) {
            throw new IllegalStateException("cannot serialise transitionstatus: " + ts, ex); // NOI18N
        }

        return t;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   t  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static TransitionStatus getTransitionStatus(final Transition t) {
        try {
            return MAPPER.readValue(t.getTransitionstatus(), TransitionStatus.class);
        } catch (final IOException ex) {
            throw new IllegalStateException("cannot deserialise transitionstatus: " + t.getTransitionstatus(), ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   <T>       DOCUMENT ME!
     * @param   clazz     DOCUMENT ME!
     * @param   original  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static <T extends BaseEntity> T cloneEntity(final Class<T> clazz, final T original) {
        try {
            return MAPPER.readValue(MAPPER.writeValueAsString(original), clazz);
        } catch (final IOException ex) {
            throw new IllegalStateException("cannot deserialise entity: " + original, ex);
        }
    }

    /**
     * Returns first data item that has a category matching the given key.
     *
     * @param   worldstate   DOCUMENT ME!
     * @param   categoryKey  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static DataItem getDataItem(final Worldstate worldstate, final String categoryKey) {
        for (final DataItem di : worldstate.getWorldstatedata()) {
            for (final Category cat : di.getCategories()) {
                if (cat.getKey().equals(categoryKey)) {
                    return di;
                }
            }
        }

        return null;
    }

    /**
     * Returns all data items that have a category matching the given key.
     *
     * @param   worldstate   DOCUMENT ME!
     * @param   categoryKey  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static List<DataItem> getDataItems(final Worldstate worldstate, final String categoryKey) {
        final List<DataItem> ret = new ArrayList<DataItem>();
        for (final DataItem di : worldstate.getWorldstatedata()) {
            for (final Category cat : di.getCategories()) {
                if (cat.getKey().equals(categoryKey)) {
                    ret.add(di);
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  worldstate  DOCUMENT ME!
     * @param  dataitems   DOCUMENT ME!
     */
    public static void attachWorldstate(final Worldstate worldstate, final List<DataItem> dataitems) {
        for (final DataItem di : dataitems) {
            di.setWorldstate(worldstate);
        }
    }
}
