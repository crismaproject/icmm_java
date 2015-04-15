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
import eu.crismaproject.icmm.icmmhelper.entity.Transition;
import eu.crismaproject.icmm.icmmhelper.entity.Worldstate;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public static final String INDICATORS_DESCRIPTOR_REF;
    public static final DataDescriptor INDICATORS_DESCRIPTOR;

    public static final List<DataItem> STATIC_ITEMS;

    static {
        WMS_DESCRIPTOR_REF = "/CRISMA.datadescriptors/1"; // NOI18N
        WMS_DESCRIPTOR = new DataDescriptor();
        WMS_DESCRIPTOR.set$ref(WMS_DESCRIPTOR_REF);

        SCHEMA_DESCRIPTOR_REF = "/CRISMA.datadescriptors/2"; // NOI18N
        SCHEMA_DESCRIPTOR = new DataDescriptor();
        SCHEMA_DESCRIPTOR.set$ref(SCHEMA_DESCRIPTOR_REF);

        INDICATORS_DESCRIPTOR_REF = "/CRISMA.datadescriptors/3"; // NOI18N
        INDICATORS_DESCRIPTOR = new DataDescriptor();
        INDICATORS_DESCRIPTOR.set$ref(INDICATORS_DESCRIPTOR_REF);

        MAPPER = new ObjectMapper(new JsonFactory());

        final List<DataItem> staticItems = new ArrayList<DataItem>();

        DataItem item = new DataItem();
        item.set$ref("/CRISMA.dataitems/1");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/2");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/3");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/4");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/5");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/6");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/7");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/8");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/9");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/10");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/11");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/12");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/13");
        staticItems.add(item);
        item = new DataItem();
        item.set$ref("/CRISMA.dataitems/14");
        staticItems.add(item);

        STATIC_ITEMS = Collections.unmodifiableList(staticItems);
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Indicators getEmtpyIndicators() {
        final Value noOfDead = new Value();
        noOfDead.setDisplayName("Number of dead");      // NOI18N
        noOfDead.setIconResource("flower_dead_16.png"); // NOI18N
        noOfDead.setUnit("People");                     // NOI18N

        final Value noOfInjured = new Value();
        noOfInjured.setDisplayName("Number of injured");      // NOI18N
        noOfInjured.setIconResource("flower_injured_16.png"); // NOI18N
        noOfInjured.setUnit("People");                        // NOI18N

        final Value noOfHomeless = new Value();
        noOfHomeless.setDisplayName("Number of homeless");      // NOI18N
        noOfHomeless.setIconResource("flower_homeless_16.png"); // NOI18N
        noOfHomeless.setUnit("People");                         // NOI18N

        final Value directDamageCost = new Value();
        directDamageCost.setDisplayName("Direct damage cost");    // NOI18N
        directDamageCost.setIconResource("dollar_direct_16.png"); // NOI18N
        directDamageCost.setUnit("Euro");                         // NOI18N

        final Value indirectDamageCost = new Value();
        indirectDamageCost.setDisplayName("Inirect damage cost");     // NOI18N
        indirectDamageCost.setIconResource("dollar_indirect_16.png"); // NOI18N
        indirectDamageCost.setUnit("Euro");                           // NOI18N

        final Value directRestorationCost = new Value();
        directRestorationCost.setDisplayName("Direct restoration cost");    // NOI18N
        directRestorationCost.setIconResource("dollar_restoration_16.png"); // NOI18N
        directRestorationCost.setUnit("Euro");                              // NOI18N

        final Value lostBuildings = new Value();
        lostBuildings.setDisplayName("Lost buildings");    // NOI18N
        lostBuildings.setIconResource("home_lost_16.png"); // NOI18N
        lostBuildings.setUnit("Buildings");                // NOI18N

        final Value unsafeBuildings = new Value();
        unsafeBuildings.setDisplayName("Unsafe buildings");    // NOI18N
        unsafeBuildings.setIconResource("home_unsafe_16.png"); // NOI18N
        unsafeBuildings.setUnit("Buildings");                  // NOI18N

        final Value totalRetrofittingCost = new Value();
        totalRetrofittingCost.setDisplayName("Total retrofitting cost");  // NOI18N
        totalRetrofittingCost.setIconResource("money_total_evac_16.png"); // NOI18N
        totalRetrofittingCost.setUnit("Euro");                            // NOI18N

        final Value noOfRetrofittedBuildings = new Value();
        noOfRetrofittedBuildings.setDisplayName("Retrofitted buildings");    // NOI18N
        noOfRetrofittedBuildings.setIconResource("money_total_evac_16.png"); // NOI18N
        noOfRetrofittedBuildings.setUnit("Buildings");                       // NOI18N

        final Value preemtiveEvacuationCost = new Value();
        preemtiveEvacuationCost.setDisplayName("Preemptive evacuation cost"); // NOI18N
        preemtiveEvacuationCost.setIconResource("money_total_evac_16.png");   // NOI18N
        preemtiveEvacuationCost.setUnit("Euro");                              // NOI18N

        final Value noOfEvacuated = new Value();
        noOfEvacuated.setDisplayName("Number of evacuated");      // NOI18N
        noOfEvacuated.setIconResource("money_total_evac_16.png"); // NOI18N
        noOfEvacuated.setUnit("People");                          // NOI18N

        final Casualties casualties = new Casualties();
        casualties.setDisplayName("Casualties");     // NOI18N
        casualties.setIconResource("flower_16.png"); // NOI18N
        casualties.setNoOfDead(noOfDead);
        casualties.setNoOfInjured(noOfInjured);
        casualties.setNoOfHomeless(indirectDamageCost);

        final Cost cost = new Cost();
        cost.setDisplayName("Economic cost");  // NOI18N
        cost.setIconResource("dollar_16.png"); // NOI18N
        cost.setDirectDamageCost(directDamageCost);
        cost.setIndirectDamageCost(indirectDamageCost);
        cost.setDirectRestorationCost(directRestorationCost);

        final DamagedBuildings damagedBuildings = new DamagedBuildings();
        damagedBuildings.setDisplayName("Damaged buildings"); // NOI18N
        damagedBuildings.setIconResource("home_16.png");      // NOI18N
        damagedBuildings.setLostBuildings(lostBuildings);
        damagedBuildings.setUnsafeBuildings(unsafeBuildings);

        final BuildingRetrofitting buildingRetrofitting = new BuildingRetrofitting();
        buildingRetrofitting.setDisplayName("Building retrofitting"); // NOI18N
        buildingRetrofitting.setIconResource("money_evac_16.png");    // NOI18N
        buildingRetrofitting.setTotalRetrofittingCost(totalRetrofittingCost);
        buildingRetrofitting.setNoOfRetrofittedBuildings(noOfRetrofittedBuildings);

        final Evacuation evacuation = new Evacuation();
        evacuation.setDisplayName("Evacuation");         // NOI18N
        evacuation.setIconResource("money_evac_16.png"); // NOI18N
        evacuation.setPreemtiveEvacuationCost(preemtiveEvacuationCost);
        evacuation.setNoOfEvacuatedPeople(noOfEvacuated);

        final Indicators indicators = new Indicators();
        indicators.setCasualties(casualties);
        indicators.setCost(cost);
        indicators.setDamagedBuildings(damagedBuildings);
        indicators.setBuildingRetrofitting(buildingRetrofitting);
        indicators.setEvacuation(evacuation);

        return indicators;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   noOfDead                  DOCUMENT ME!
     * @param   noOfInjured               DOCUMENT ME!
     * @param   noOfHomeless              DOCUMENT ME!
     * @param   directDamageCost          DOCUMENT ME!
     * @param   indirectDamageCost        DOCUMENT ME!
     * @param   directRestorationCost     DOCUMENT ME!
     * @param   lostBuildings             DOCUMENT ME!
     * @param   unsafeBuildings           DOCUMENT ME!
     * @param   preemtiveEvacuationCost   DOCUMENT ME!
     * @param   noOfEvacuated             DOCUMENT ME!
     * @param   totalRetrofittingCost     DOCUMENT ME!
     * @param   noOfRetrofittedBuildings  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Indicators getIndicators(
            final Number noOfDead,
            final Number noOfInjured,
            final Number noOfHomeless,
            final Number directDamageCost,
            final Number indirectDamageCost,
            final Number directRestorationCost,
            final Number lostBuildings,
            final Number unsafeBuildings,
            final Number preemtiveEvacuationCost,
            final Number noOfEvacuated,
            final Number totalRetrofittingCost,
            final Number noOfRetrofittedBuildings) {
        final Indicators indicators = getEmtpyIndicators();

        indicators.getCasualties().getNoOfDead().setValue(noOfDead);
        indicators.getCasualties().getNoOfInjured().setValue(noOfInjured);
        indicators.getCasualties().getNoOfHomeless().setValue(noOfHomeless);

        indicators.getCost().getDirectDamageCost().setValue(directDamageCost);
        indicators.getCost().getIndirectDamageCost().setValue(indirectDamageCost);
        indicators.getCost().getDirectRestorationCost().setValue(directRestorationCost);

        indicators.getDamagedBuildings().getLostBuildings().setValue(lostBuildings);
        indicators.getDamagedBuildings().getUnsafeBuildings().setValue(unsafeBuildings);

        indicators.getBuildingRetrofitting().getTotalRetrofittingCost().setValue(totalRetrofittingCost);
        indicators.getBuildingRetrofitting().getNoOfRetrofittedBuildings().setValue(noOfRetrofittedBuildings);

        indicators.getEvacuation().getPreemtiveEvacuationCost().setValue(preemtiveEvacuationCost);
        indicators.getEvacuation().getNoOfEvacuatedPeople().setValue(noOfEvacuated);

        return indicators;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   indicators  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public static DataItem getIndicatorDataItem(final Indicators indicators) {
        final DataItem indicatorItem = new DataItem();

        indicatorItem.setDatadescriptor(INDICATORS_DESCRIPTOR);
        indicatorItem.setName("indicators dataitem");
        indicatorItem.setDescription("dataitem for indicators");          // NOI18N
        indicatorItem.setActualaccessinfocontenttype("application/json"); // NOI18N
        indicatorItem.setCategories(Arrays.asList(new Category[] { Categories.INDICATORS.getCategory() }));
        indicatorItem.setLastmodified(new Date());

        try {
            indicatorItem.setActualaccessinfo(MAPPER.writeValueAsString(indicators));
        } catch (final JsonProcessingException ex) {
            throw new IllegalStateException("I DON'T EVER OCCUR", ex); // NOI18N
        }

        return indicatorItem;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   originWs         DOCUMENT ME!
     * @param   resultDataItems  DOCUMENT ME!
     * @param   indicatorItem    DOCUMENT ME!
     * @param   transition       DOCUMENT ME!
     * @param   name             DOCUMENT ME!
     * @param   description      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Worldstate getWorldstate(final Worldstate originWs,
            final List<DataItem> resultDataItems,
            final DataItem indicatorItem,
            final Transition transition,
            final String name,
            final String description) {
        final Worldstate newWs = new Worldstate();
        newWs.setCategories(originWs.getCategories());
        newWs.setCreated(new Date());
        newWs.setDescription(description);
        newWs.setIccdata(indicatorItem);
        newWs.setName(name);
        newWs.setOrigintransition(transition);
        newWs.setParentworldstate(originWs);

        final List<DataItem> items = new ArrayList<DataItem>();
        items.addAll(STATIC_ITEMS);
        items.addAll(resultDataItems);
        newWs.setWorldstatedata(items);

        return newWs;
    }
}
