package org.mobiloc.lobgasp.osm.model.Ways;

import javax.persistence.*;
import org.mobiloc.lobgasp.model.SpatialDBEntity;
import org.mobiloc.lobgasp.osm.model.WayEntity;
import org.mobiloc.lobgasp.osm.parser.model.AbstractNode;
import org.mobiloc.lobgasp.osm.parser.model.Way;

/**
 *
 * @author rainerdreyer
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TunnelEntity extends WayEntity {

    @Override
    public SpatialDBEntity construct(AbstractNode in)
    {
        this.setName(in.tags.get("name"));
        this.setOSMid(Long.parseLong(in.id));
        this.setGeom(((Way) in).getLineString().buffer(0.0001));
        return this;
    }

    @Override
    public boolean xmlRule(AbstractNode in) {
        if (in.tags.containsKey("highway") && in.tags.containsKey("tunnel")) {
            return true;
        }
        return false;
    }

}
