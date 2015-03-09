/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.1
 */
@Provider
public final class ISODateJacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    //~ Instance fields --------------------------------------------------------

    private final ObjectMapper mapper;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ISODateJacksonObjectMapperProvider object.
     */
    public ISODateJacksonObjectMapperProvider() {
        mapper = new ObjectMapper(new JsonFactory());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return mapper;
    }
}
