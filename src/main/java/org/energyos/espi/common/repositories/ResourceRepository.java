package org.energyos.espi.common.repositories;

import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.domain.UsagePoint;

public interface ResourceRepository {
    void persist(IdentifiedObject resource);

    List<IdentifiedObject> findAllParentsByRelatedHref(String href, Linkable linkable);

    List<IdentifiedObject> findAllRelated(Linkable linkable);

    <T> T findByUUID(UUID uuid, Class<T> clazz);

    UsagePoint findByUUID(UUID uuid);

    void update(UsagePoint resource);

    <T extends IdentifiedObject> T findById(Long id, Class<T> clazz);

    <T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz);

    <T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(Long usagePointId, Class<T> clazz);

    <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Long id2, Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Long id2, Long id3, Class<T> clazz);

}
