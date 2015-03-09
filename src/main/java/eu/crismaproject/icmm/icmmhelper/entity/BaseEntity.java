/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package eu.crismaproject.icmm.icmmhelper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  0.1
 */
@XmlRootElement
@NoArgsConstructor
@ToString(of = { "$self", "$ref", "id" })
@EqualsAndHashCode(of = { "$self", "$ref", "id" })
public abstract class BaseEntity {

    //~ Instance fields --------------------------------------------------------

    @XmlElement(required = false)
    @Getter
    @Setter
    private String $self;
    @XmlElement(required = false)
    @Getter
    @Setter
    private String $ref;
    @XmlElement(required = false)
    @Getter
    @Setter
    private Integer id;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BaseEntity object.
     *
     * @param  $self  DOCUMENT ME!
     * @param  $ref   DOCUMENT ME!
     * @param  id     DOCUMENT ME!
     */
    public BaseEntity(final String $self, final String $ref, final Integer id) {
        this.$self = $self;
        this.$ref = $ref;
        this.id = id;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public String getRef() {
        return ($ref == null) ? $self : $ref;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public abstract String getEntityName();
}
