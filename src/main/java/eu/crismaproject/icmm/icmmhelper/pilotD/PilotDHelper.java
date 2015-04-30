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
 * @version  0.2
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
        final Value noOfDeadMin = new Value();
        noOfDeadMin.setDisplayName("Number of dead (Min)");              // NOI18N
        noOfDeadMin.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfDeadMin.setUnit("People");                                   // NOI18N
        final Value noOfDeadAvg = new Value();
        noOfDeadAvg.setDisplayName("Number of dead (Avg)");              // NOI18N
        noOfDeadAvg.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfDeadAvg.setUnit("People");                                   // NOI18N
        final Value noOfDeadMax = new Value();
        noOfDeadMax.setDisplayName("Number of dead (Max)");              // NOI18N
        noOfDeadMax.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfDeadMax.setUnit("People");                                   // NOI18N

        final Value noOfInjuredMin = new Value();
        noOfInjuredMin.setDisplayName("Number of injured (Min)");           // NOI18N
        noOfInjuredMin.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfInjuredMin.setUnit("People");                                   // NOI18N
        final Value noOfInjuredAvg = new Value();
        noOfInjuredAvg.setDisplayName("Number of injured (Avg)");           // NOI18N
        noOfInjuredAvg.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfInjuredAvg.setUnit("People");                                   // NOI18N
        final Value noOfInjuredMax = new Value();
        noOfInjuredMax.setDisplayName("Number of injured (Max)");           // NOI18N
        noOfInjuredMax.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfInjuredMax.setUnit("People");                                   // NOI18N

        final Value noOfHomelessMin = new Value();
        noOfHomelessMin.setDisplayName("Number of homeless (Min)");          // NOI18N
        noOfHomelessMin.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfHomelessMin.setUnit("People");                                   // NOI18N
        final Value noOfHomelessAvg = new Value();
        noOfHomelessAvg.setDisplayName("Number of homeless (Avg)");          // NOI18N
        noOfHomelessAvg.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfHomelessAvg.setUnit("People");                                   // NOI18N
        final Value noOfHomelessMax = new Value();
        noOfHomelessMax.setDisplayName("Number of homeless (Max)");          // NOI18N
        noOfHomelessMax.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfHomelessMax.setUnit("People");                                   // NOI18N

        final Value directDamageCostMin = new Value();
        directDamageCostMin.setDisplayName("Direct damage cost (Min)");        // NOI18N
        directDamageCostMin.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        directDamageCostMin.setUnit("Euro");                                   // NOI18N
        final Value directDamageCostAvg = new Value();
        directDamageCostAvg.setDisplayName("Direct damage cost (Avg)");        // NOI18N
        directDamageCostAvg.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        directDamageCostAvg.setUnit("Euro");                                   // NOI18N
        final Value directDamageCostMax = new Value();
        directDamageCostMax.setDisplayName("Direct damage cost (Max)");        // NOI18N
        directDamageCostMax.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        directDamageCostMax.setUnit("Euro");                                   // NOI18N

        final Value indirectDamageCostMin = new Value();
        indirectDamageCostMin.setDisplayName("Indirect damage cost (Min)");      // NOI18N
        indirectDamageCostMin.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        indirectDamageCostMin.setUnit("Euro");                                   // NOI18N
        final Value indirectDamageCostAvg = new Value();
        indirectDamageCostAvg.setDisplayName("Indirect damage cost (Avg)");      // NOI18N
        indirectDamageCostAvg.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        indirectDamageCostAvg.setUnit("Euro");                                   // NOI18N
        final Value indirectDamageCostMax = new Value();
        indirectDamageCostMax.setDisplayName("Indirect damage cost (Max)");      // NOI18N
        indirectDamageCostMax.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        indirectDamageCostMax.setUnit("Euro");                                   // NOI18N

        final Value directRestorationCostMin = new Value();
        directRestorationCostMin.setDisplayName("Direct restoration cost (Min)");   // NOI18N
        directRestorationCostMin.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        directRestorationCostMin.setUnit("Euro");                                   // NOI18N
        final Value directRestorationCostAvg = new Value();
        directRestorationCostAvg.setDisplayName("Direct restoration cost (Avg)");   // NOI18N
        directRestorationCostAvg.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        directRestorationCostAvg.setUnit("Euro");                                   // NOI18N
        final Value directRestorationCostMax = new Value();
        directRestorationCostMax.setDisplayName("Direct restoration cost (Max)");   // NOI18N
        directRestorationCostMax.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        directRestorationCostMax.setUnit("Euro");                                   // NOI18N

        final Value lostBuildingsMin = new Value();
        lostBuildingsMin.setDisplayName("Lost buildings (Min)");               // NOI18N
        lostBuildingsMin.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        lostBuildingsMin.setUnit("Buildings");                                 // NOI18N
        final Value lostBuildingsAvg = new Value();
        lostBuildingsAvg.setDisplayName("Lost buildings (Avg)");               // NOI18N
        lostBuildingsAvg.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        lostBuildingsAvg.setUnit("Buildings");                                 // NOI18N
        final Value lostBuildingsMax = new Value();
        lostBuildingsMax.setDisplayName("Lost buildings (Max)");               // NOI18N
        lostBuildingsMax.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        lostBuildingsMax.setUnit("Buildings");                                 // NOI18N

        final Value unsafeBuildingsMin = new Value();
        unsafeBuildingsMin.setDisplayName("Unsafe buildings (Min)");             // NOI18N
        unsafeBuildingsMin.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        unsafeBuildingsMin.setUnit("Buildings");                                 // NOI18N
        final Value unsafeBuildingsAvg = new Value();
        unsafeBuildingsAvg.setDisplayName("Unsafe buildings (Avg)");             // NOI18N
        unsafeBuildingsAvg.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        unsafeBuildingsAvg.setUnit("Buildings");                                 // NOI18N
        final Value unsafeBuildingsMax = new Value();
        unsafeBuildingsMax.setDisplayName("Unsafe buildings (Max)");             // NOI18N
        unsafeBuildingsMax.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        unsafeBuildingsMax.setUnit("Buildings");                                 // NOI18N

        final Value totalRetrofittingCost = new Value();
        totalRetrofittingCost.setDisplayName("Total retrofitting cost");         // NOI18N
        totalRetrofittingCost.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        totalRetrofittingCost.setUnit("Euro");                                   // NOI18N

        final Value noOfRetrofittedBuildings = new Value();
        noOfRetrofittedBuildings.setDisplayName("Retrofitted buildings");              // NOI18N
        noOfRetrofittedBuildings.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        noOfRetrofittedBuildings.setUnit("Buildings");                                 // NOI18N

        final Value preemtiveEvacuationCost = new Value();
        preemtiveEvacuationCost.setDisplayName("Preemptive evacuation cost");      // NOI18N
        preemtiveEvacuationCost.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        preemtiveEvacuationCost.setUnit("Euro");                                   // NOI18N

        final Value noOfEvacuated = new Value();
        noOfEvacuated.setDisplayName("Number of evacuated");               // NOI18N
        noOfEvacuated.setIconResource("images/glyphicons-500-family.png"); // NOI18N
        noOfEvacuated.setUnit("People");                                   // NOI18N

        final Casualties casualties = new Casualties();
        casualties.setDisplayName("Casualties");                       // NOI18N
        casualties.setIconResource("images/glyphicons-291-skull.png"); // NOI18N
        casualties.setNoOfDeadMin(noOfDeadMin);
        casualties.setNoOfInjuredMin(noOfInjuredMin);
        casualties.setNoOfHomelessMin(noOfHomelessMin);
        casualties.setNoOfDeadAvg(noOfDeadAvg);
        casualties.setNoOfInjuredAvg(noOfInjuredAvg);
        casualties.setNoOfHomelessAvg(noOfHomelessAvg);
        casualties.setNoOfDeadMax(noOfDeadMax);
        casualties.setNoOfInjuredMax(noOfInjuredMax);
        casualties.setNoOfHomelessMax(noOfHomelessMax);

        final Cost cost = new Cost();
        cost.setDisplayName("Economic cost");                   // NOI18N
        cost.setIconResource("images/glyphicons-227-euro.png"); // NOI18N
        cost.setDirectDamageCostMin(directDamageCostMin);
        cost.setIndirectDamageCostMin(indirectDamageCostMin);
        cost.setDirectRestorationCostMin(directRestorationCostMin);
        cost.setDirectDamageCostAvg(directDamageCostAvg);
        cost.setIndirectDamageCostAvg(indirectDamageCostAvg);
        cost.setDirectRestorationCostAvg(directRestorationCostAvg);
        cost.setDirectDamageCostMax(directDamageCostMax);
        cost.setIndirectDamageCostMax(indirectDamageCostMax);
        cost.setDirectRestorationCostMax(directRestorationCostMax);

        final DamagedBuildings damagedBuildings = new DamagedBuildings();
        damagedBuildings.setDisplayName("Damaged buildings");                  // NOI18N
        damagedBuildings.setIconResource("images/glyphicons-90-building.png"); // NOI18N
        damagedBuildings.setLostBuildingsMin(lostBuildingsMin);
        damagedBuildings.setUnsafeBuildingsMin(unsafeBuildingsMin);
        damagedBuildings.setLostBuildingsAvg(lostBuildingsAvg);
        damagedBuildings.setUnsafeBuildingsAvg(unsafeBuildingsAvg);
        damagedBuildings.setLostBuildingsMax(lostBuildingsMax);
        damagedBuildings.setUnsafeBuildingsMax(unsafeBuildingsMax);

        final BuildingRetrofitting buildingRetrofitting = new BuildingRetrofitting();
        buildingRetrofitting.setDisplayName("Building retrofitting");                     // NOI18N
        buildingRetrofitting.setIconResource("images/glyphicons-376-classic-hammer.png"); // NOI18N
        buildingRetrofitting.setTotalRetrofittingCost(totalRetrofittingCost);
        buildingRetrofitting.setNoOfRetrofittedBuildings(noOfRetrofittedBuildings);

        final Evacuation evacuation = new Evacuation();
        evacuation.setDisplayName("Evacuation");                                // NOI18N
        evacuation.setIconResource("images/glyphicons-592-person-running.png"); // NOI18N
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
     * @param   noOfDeadMin               DOCUMENT ME!
     * @param   noOfInjuredMin            DOCUMENT ME!
     * @param   noOfHomelessMin           DOCUMENT ME!
     * @param   noOfDeadAvg               DOCUMENT ME!
     * @param   noOfInjuredAvg            DOCUMENT ME!
     * @param   noOfHomelessAvg           DOCUMENT ME!
     * @param   noOfDeadMax               DOCUMENT ME!
     * @param   noOfInjuredMax            DOCUMENT ME!
     * @param   noOfHomelessMax           DOCUMENT ME!
     * @param   directDamageCostMin       DOCUMENT ME!
     * @param   indirectDamageCostMin     DOCUMENT ME!
     * @param   directRestorationCostMin  DOCUMENT ME!
     * @param   directDamageCostAvg       DOCUMENT ME!
     * @param   indirectDamageCostAvg     DOCUMENT ME!
     * @param   directRestorationCostAvg  DOCUMENT ME!
     * @param   directDamageCostMax       DOCUMENT ME!
     * @param   indirectDamageCostMax     DOCUMENT ME!
     * @param   directRestorationCostMax  DOCUMENT ME!
     * @param   lostBuildingsMin          DOCUMENT ME!
     * @param   unsafeBuildingsMin        DOCUMENT ME!
     * @param   lostBuildingsAvg          DOCUMENT ME!
     * @param   unsafeBuildingsAvg        DOCUMENT ME!
     * @param   lostBuildingsMax          DOCUMENT ME!
     * @param   unsafeBuildingsMax        DOCUMENT ME!
     * @param   preemtiveEvacuationCost   DOCUMENT ME!
     * @param   noOfEvacuated             DOCUMENT ME!
     * @param   totalRetrofittingCost     DOCUMENT ME!
     * @param   noOfRetrofittedBuildings  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public static Indicators getIndicators(
            final Number noOfDeadMin,
            final Number noOfInjuredMin,
            final Number noOfHomelessMin,
            final Number noOfDeadAvg,
            final Number noOfInjuredAvg,
            final Number noOfHomelessAvg,
            final Number noOfDeadMax,
            final Number noOfInjuredMax,
            final Number noOfHomelessMax,
            final Number directDamageCostMin,
            final Number indirectDamageCostMin,
            final Number directRestorationCostMin,
            final Number directDamageCostAvg,
            final Number indirectDamageCostAvg,
            final Number directRestorationCostAvg,
            final Number directDamageCostMax,
            final Number indirectDamageCostMax,
            final Number directRestorationCostMax,
            final Number lostBuildingsMin,
            final Number unsafeBuildingsMin,
            final Number lostBuildingsAvg,
            final Number unsafeBuildingsAvg,
            final Number lostBuildingsMax,
            final Number unsafeBuildingsMax,
            final Number preemtiveEvacuationCost,
            final Number noOfEvacuated,
            final Number totalRetrofittingCost,
            final Number noOfRetrofittedBuildings) {
        if (!nonNull((Object[])new Number[] {
                            noOfDeadMin,
                            noOfInjuredMin,
                            noOfHomelessMin,
                            noOfDeadAvg,
                            noOfInjuredAvg,
                            noOfHomelessAvg,
                            noOfDeadMax,
                            noOfInjuredMax,
                            noOfHomelessMax,
                            directDamageCostMin,
                            indirectDamageCostMin,
                            directRestorationCostMin,
                            directDamageCostAvg,
                            indirectDamageCostAvg,
                            directRestorationCostAvg,
                            directDamageCostMax,
                            indirectDamageCostMax,
                            directRestorationCostMax,
                            lostBuildingsMin,
                            unsafeBuildingsMin,
                            lostBuildingsAvg,
                            unsafeBuildingsAvg,
                            lostBuildingsMax,
                            unsafeBuildingsMax,
                            preemtiveEvacuationCost,
                            noOfEvacuated,
                            totalRetrofittingCost,
                            noOfRetrofittedBuildings
                        })) {
            throw new IllegalArgumentException("null is not a valid number");
        }

        final Indicators indicators = getEmtpyIndicators();

        indicators.getCasualties().getNoOfDeadMin().setValue(noOfDeadMin);
        indicators.getCasualties().getNoOfInjuredMin().setValue(noOfInjuredMin);
        indicators.getCasualties().getNoOfHomelessMin().setValue(noOfHomelessMin);
        indicators.getCasualties().getNoOfDeadAvg().setValue(noOfDeadAvg);
        indicators.getCasualties().getNoOfInjuredAvg().setValue(noOfInjuredAvg);
        indicators.getCasualties().getNoOfHomelessAvg().setValue(noOfHomelessAvg);
        indicators.getCasualties().getNoOfDeadMax().setValue(noOfDeadMax);
        indicators.getCasualties().getNoOfInjuredMax().setValue(noOfInjuredMax);
        indicators.getCasualties().getNoOfHomelessMax().setValue(noOfHomelessMax);

        indicators.getCost().getDirectDamageCostMin().setValue(directDamageCostMin);
        indicators.getCost().getIndirectDamageCostMin().setValue(indirectDamageCostMin);
        indicators.getCost().getDirectRestorationCostMin().setValue(directRestorationCostMin);
        indicators.getCost().getDirectDamageCostAvg().setValue(directDamageCostAvg);
        indicators.getCost().getIndirectDamageCostAvg().setValue(indirectDamageCostAvg);
        indicators.getCost().getDirectRestorationCostAvg().setValue(directRestorationCostAvg);
        indicators.getCost().getDirectDamageCostMax().setValue(directDamageCostMax);
        indicators.getCost().getIndirectDamageCostMax().setValue(indirectDamageCostMax);
        indicators.getCost().getDirectRestorationCostMax().setValue(directRestorationCostMax);

        indicators.getDamagedBuildings().getLostBuildingsMin().setValue(lostBuildingsMin);
        indicators.getDamagedBuildings().getUnsafeBuildingsMin().setValue(unsafeBuildingsMin);
        indicators.getDamagedBuildings().getLostBuildingsAvg().setValue(lostBuildingsAvg);
        indicators.getDamagedBuildings().getUnsafeBuildingsAvg().setValue(unsafeBuildingsAvg);
        indicators.getDamagedBuildings().getLostBuildingsMax().setValue(lostBuildingsMax);
        indicators.getDamagedBuildings().getUnsafeBuildingsMax().setValue(unsafeBuildingsMax);

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

    /**
     * DOCUMENT ME!
     *
     * @param   obs  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean nonNull(final Object... obs) {
        if (obs == null) {
            return false;
        }

        for (final Object o : obs) {
            if (o == null) {
                return false;
            }
        }

        return true;
    }
}
