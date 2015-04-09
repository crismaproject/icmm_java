/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.pilotD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.1
 */
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Indicators {

    //~ Instance fields --------------------------------------------------------

    private Casualties casualties;
    private Cost cost;
    private DamagedBuildings damagedBuildings;
    private BuildingRetrofitting buildingRetrofitting;
    private Evacuation evacuation;
}
