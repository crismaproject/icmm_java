/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.pilotD;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.crismaproject.icmm.icmmhelper.ICMMHelper;
import eu.crismaproject.icmm.icmmhelper.entity.Category;
import eu.crismaproject.icmm.icmmhelper.entity.DataDescriptor;
import eu.crismaproject.icmm.icmmhelper.entity.DataItem;
import eu.crismaproject.icmm.icmmhelper.entity.Worldstate;

import java.io.IOException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  0.1
 */
public class PilotDHelper {

    //~ Static fields/initializers ---------------------------------------------

    private static final ObjectMapper MAPPER;

    public static final String WMS_DESCRIPTOR_REF;
    public static final DataDescriptor WMS_DESCRIPTOR;

    public static final String SCHEMA_DESCRIPTOR_REF;
    public static final DataDescriptor SCHEMA_DESCRIPTOR;

    static {
        WMS_DESCRIPTOR_REF = "/CRISMA.datadescriptors/1"; // NOI18N
        WMS_DESCRIPTOR = new DataDescriptor();
        WMS_DESCRIPTOR.set$ref(WMS_DESCRIPTOR_REF);

        SCHEMA_DESCRIPTOR_REF = "/CRISMA.datadescriptors/2"; // NOI18N
        SCHEMA_DESCRIPTOR = new DataDescriptor();
        SCHEMA_DESCRIPTOR.set$ref(SCHEMA_DESCRIPTOR_REF);

        MAPPER = new ObjectMapper(new JsonFactory());
    }

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PilotDHelper object.
     */
    private PilotDHelper() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   worldstate  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static String getBaseSchema(final Worldstate worldstate) {
        final DataItem schemaItem = ICMMHelper.getDataItem(worldstate, Categories.SCHEMA.toString());

        if (schemaItem == null) {
            throw new IllegalStateException("schema dataitem not found: " + worldstate);       // NOI18N
        }
        if (schemaItem.getDatadescriptor() == null) {
            throw new IllegalStateException("schema datadescriptor not found: " + schemaItem); // NOI18N
        }

        try {
            final Schema schema = MAPPER.readValue(schemaItem.getDatadescriptor().getDefaultaccessinfo(), Schema.class);

            return schema.getSchemaname();
        } catch (final IOException ex) {
            throw new IllegalStateException("illegal content of defaultaccessinfo", ex); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   worldstate  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static String getSchema(final Worldstate worldstate) {
        final DataItem schemaItem = ICMMHelper.getDataItem(worldstate, Categories.SCHEMA.toString());

        if (schemaItem == null) {
            throw new IllegalStateException("schema dataitem not found: " + worldstate); // NOI18N
        }

        try {
            final Schema schema = MAPPER.readValue(schemaItem.getActualaccessinfo(), Schema.class);

            return schema.getSchemaname();
        } catch (final IOException ex) {
            throw new IllegalStateException("illegal content of actualaccessinfo", ex); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   schemaName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static DataItem getSchemaItem(final String schemaName) {
        final DataItem schemaItem = new DataItem();

        schemaItem.setDatadescriptor(SCHEMA_DESCRIPTOR);
        schemaItem.setName("Current schema");                                                             // NOI18N
        schemaItem.setDescription("The internal name of the schema holding the data of this worldstate"); // NOI18N
        schemaItem.setActualaccessinfocontenttype("application/json");
        schemaItem.setCategories(Arrays.asList(new Category[] { Categories.SCHEMA.getCategory() }));
        schemaItem.setLastmodified(new Date());

        try {
            schemaItem.setActualaccessinfo(MAPPER.writeValueAsString(new Schema(schemaName)));
        } catch (final JsonProcessingException ex) {
            throw new IllegalStateException("I DON'T EVER OCCUR", ex); // NOI18N
        }

        return schemaItem;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   layername    DOCUMENT ME!
     * @param   displayname  DOCUMENT ME!
     * @param   category     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static DataItem getWmsDataItem(final String layername, final String displayname, final Categories category) {
        final DataItem wmsItem = new DataItem();

        wmsItem.setDatadescriptor(WMS_DESCRIPTOR);
        wmsItem.setName(layername);
        wmsItem.setDescription("dataitem for '" + layername + "'"); // NOI18N
        wmsItem.setActualaccessinfocontenttype("application/json"); // NOI18N
        wmsItem.setCategories(Arrays.asList(new Category[] { category.getCategory() }));
        wmsItem.setLastmodified(new Date());

        try {
            wmsItem.setActualaccessinfo(MAPPER.writeValueAsString(
                    new WmsLayer(
                        layername,
                        (displayname == null) ? layername : displayname,
                        false,
                        true)));
        } catch (final JsonProcessingException ex) {
            throw new IllegalStateException("I DON'T EVER OCCUR", ex); // NOI18N
        }

        return wmsItem;
    }
}
