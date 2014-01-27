/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.repositories.jpa;


import static org.energyos.espi.common.test.EspiFactory.newMeterReading;
import static org.energyos.espi.common.test.EspiFactory.newSubscription;
import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;
import static org.energyos.espi.common.test.IsEmpty.isEmpty;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.ServiceCategory;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.common.test.EspiPersistenceFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class UsagePointRepositoryImplTests {

    @Autowired
    UsagePointRepository repository;
    @Autowired
    RetailCustomerRepository retailCustomerRepository;
    @Autowired
    ApplicationInformationRepository applicationInformationRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private EspiPersistenceFactory factory;
    private RetailCustomer customer;
    private UUID uuid;

    @Before
    public void setup() {
        customer = new RetailCustomer();
        customer.setId(1L);
        uuid = UUID.randomUUID();
    }

    @Test
    public void findByRetailCustomer_returnsUsagePointsByCustomer() {
        RetailCustomer customer1 = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(customer1);

        UsagePoint usagePoint = newUsagePoint(customer1);
        repository.persist(usagePoint);

        assertEquals(1, repository.findAllByRetailCustomerId(customer1.getId()).size());
    }

    @Test
    public void findById_returnsUsagePoint() {
        UsagePoint usagePoint = factory.createUsagePoint();
        UsagePoint retrievedUsagePoint = repository.findById(usagePoint.getId());
        assertNotNull("The usage point was null.", retrievedUsagePoint);
    }

    @Test
    public void persist_withNewUsagePoint_persistsUsagePoint() throws Exception {
        UsagePoint usagePoint = getUsagePoint();

        repository.persist(usagePoint);

        assertNotNull(usagePoint.getId());
        assertNotNull(usagePoint.getRetailCustomer());
    }

    private UsagePoint getUsagePoint() {
        UsagePoint usagePoint = newUsagePoint();
        usagePoint.setMRID("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0");
        return usagePoint;
    }

    @Test
    public void persist_savesMeterReading() {
        UsagePoint usagePoint = getUsagePoint();
        MeterReading meterReading = newMeterReading();

        usagePoint.addMeterReading(meterReading);

        repository.persist(usagePoint);

        assertNotNull("MeterReading id was null", usagePoint.getMeterReadings().get(0).getId());
        assertNotNull("MeterReading usagePoint is null", usagePoint.getMeterReadings().get(0).getUsagePoint());
    }

    @Test
    public void persist_savesPublishedAndUpdated() {
        RetailCustomer customer1 = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(customer1);

        UsagePoint usagePoint = newUsagePoint(customer1);

        assertThat(usagePoint.getUpdated(), is(notNullValue()));
        assertThat(usagePoint.getPublished(), is(notNullValue()));

        repository.persist(usagePoint);

        UsagePoint persistedUsagePoint = repository.findById(usagePoint.getId());

        assertThat(usagePoint.getUpdated(), is(persistedUsagePoint.getUpdated()));
        assertThat(usagePoint.getPublished(), is(persistedUsagePoint.getPublished()));
    }

    @Test
    public void persist_savesIntervalBlocks() {
        UsagePoint usagePoint = getUsagePoint();
        MeterReading meterReading = newMeterReading();
        IntervalBlock intervalBlock = new IntervalBlock();
        intervalBlock.setUUID(UUID.randomUUID());

        meterReading.getIntervalBlocks().add(intervalBlock);
        usagePoint.getMeterReadings().add(meterReading);

        repository.persist(usagePoint);

        assertNotNull(usagePoint.getMeterReadings().get(0).getIntervalBlocks().get(0).getId());
    }

    @Test
    public void createOrReplaceByUUID_givenNewUsagePoint_savesUsagePoint() {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(UUID.randomUUID());
        repository.createOrReplaceByUUID(usagePoint);

        assertNotNull(usagePoint.getId());
    }

    @Test
    public void createOrReplaceByUUID_givenExistingUsagePoint_updatesUsagePoint() {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);
        repository.createOrReplaceByUUID(updatedUsagePoint);

        assertEquals(usagePoint.getId(), updatedUsagePoint.getId());
    }

    @Test
    public void createOrReplaceByUUID_givenExistingUsagePoint_updatesServiceCategory() {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);
        updatedUsagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.GAS_SERVICE));
        repository.createOrReplaceByUUID(updatedUsagePoint);

        updatedUsagePoint = repository.findById(updatedUsagePoint.getId());

        assertEquals(ServiceCategory.GAS_SERVICE, updatedUsagePoint.getServiceCategory().getKind());
    }

    @Test
    public void createOrReplaceByUUID_givenUsagePointWithNoCustomer_doesNotUpdateRetailCustomer() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);
        UsagePoint usagePoint = newUsagePoint(retailCustomer);
        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = newUsagePoint(null);
        updatedUsagePoint.setUUID(usagePoint.getUUID());
        repository.createOrReplaceByUUID(updatedUsagePoint);

        updatedUsagePoint = repository.findById(updatedUsagePoint.getId());

        assertEquals(retailCustomer.getId(), updatedUsagePoint.getRetailCustomer().getId());
    }

    @Test
    public void createOrReplaceByUUID_givenUsagePointWithCustomer_updatesRetailCustomer() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);
        UsagePoint usagePoint = newUsagePoint(retailCustomer);
        repository.createOrReplaceByUUID(usagePoint);

        RetailCustomer newRetailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(newRetailCustomer);
        UsagePoint updatedUsagePoint = newUsagePoint(newRetailCustomer);
        updatedUsagePoint.setUUID(usagePoint.getUUID());

        repository.createOrReplaceByUUID(updatedUsagePoint);

        updatedUsagePoint = repository.findById(updatedUsagePoint.getId());

        assertEquals(newRetailCustomer.getId(), updatedUsagePoint.getRetailCustomer().getId());
    }

    @Test
    public void createOrReplaceByUUID_replacesDescription() {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);
        updatedUsagePoint.setDescription("New description");

        repository.createOrReplaceByUUID(updatedUsagePoint);

        usagePoint = repository.findByUUID(uuid);

        assertEquals("New description", usagePoint.getDescription());
    }

    @Test
    public void createOrReplaceByUUID_replacesServiceCategory() {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);

        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);
        updatedUsagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.GAS_SERVICE));

        repository.createOrReplaceByUUID(updatedUsagePoint);

        usagePoint = repository.findByUUID(uuid);

        assertEquals(ServiceCategory.GAS_SERVICE, usagePoint.getServiceCategory().getKind());
    }

    @Test
    public void createOrReplaceByUUID_replacesMeterReadings() {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        MeterReading meterReading = newMeterReading();
        usagePoint.addMeterReading(meterReading);

        assertTrue(usagePoint.getMeterReadings().size() > 0);

        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);
        updatedUsagePoint.addMeterReading(newMeterReading());
        updatedUsagePoint.addMeterReading(newMeterReading());

        repository.createOrReplaceByUUID(updatedUsagePoint);

        usagePoint = repository.findByUUID(uuid);

        assertTrue(usagePoint.getMeterReadings().size() == 1);
    }

    @Test
    public void createOrReplaceByUUID_replacesElectricPowerUsageSummaries() {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);

        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);
        ElectricPowerUsageSummary electricPowerUsageSummary = new ElectricPowerUsageSummary();
        electricPowerUsageSummary.setUUID(UUID.randomUUID());
        updatedUsagePoint.addElectricPowerUsageSummary(electricPowerUsageSummary);

        repository.createOrReplaceByUUID(updatedUsagePoint);

        usagePoint = repository.findByUUID(uuid);

        assertTrue(usagePoint.getElectricPowerUsageSummaries().size() == 1);
    }

    @Test
    public void createOrReplaceByUUID_replacesSelfLink() throws Exception {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        usagePoint.setSelfLink(new LinkType(LinkType.SELF, "self"));

        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);

        repository.createOrReplaceByUUID(updatedUsagePoint);

        usagePoint = repository.findByUUID(uuid);

        assertThat(usagePoint.getSelfLink(), is(not(nullValue())));
    }

    @Test
    public void createOrReplaceByUUID_replacesUpLink() throws Exception {
        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        usagePoint.setUpLink(new LinkType(LinkType.UP, "up"));

        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = EspiFactory.newUsagePointOnly(uuid);

        repository.createOrReplaceByUUID(updatedUsagePoint);

        usagePoint = repository.findByUUID(uuid);

        assertThat(usagePoint.getUpLink(), is(not(nullValue())));
    }

    @Test
    public void findByUUID() {
        UsagePoint usagePoint = new UsagePoint();
        usagePoint.setMRID("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0");
        usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));
        repository.persist(usagePoint);

        UsagePoint savedUsagePoint = repository.findByUUID(usagePoint.getUUID());

        assertThat(usagePoint, is(equalTo(savedUsagePoint)));
    }

    @Test
    public void associateByUUID_setsRetailCustomer() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);

        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        repository.persist(usagePoint);

        repository.associateByUUID(retailCustomer, uuid);

        assertEquals(retailCustomer.getId(), usagePoint.getRetailCustomer().getId());
    }

    @Test
    public void associateByUUID_retainsDescription() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);

        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        String description = usagePoint.getDescription();
        repository.persist(usagePoint);

        repository.associateByUUID(retailCustomer, uuid);

        assertEquals(description, usagePoint.getDescription());
    }

    @Test
    public void associateByUUID_retainsMeterReadings() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);

        UsagePoint usagePoint = EspiFactory.newUsagePointOnly(uuid);
        usagePoint.addMeterReading(newMeterReading());
        repository.persist(usagePoint);

        repository.associateByUUID(retailCustomer, uuid);
        assertTrue(usagePoint.getMeterReadings().size() > 0);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteById() {
        UsagePoint usagePoint = factory.createUsagePoint();

        repository.deleteById(usagePoint.getId());

        repository.findById(usagePoint.getId());
    }

    @Test
    public void persist_savesReadingTypes() throws Exception {
        UsagePoint usagePoint = newUsagePoint();
        MeterReading meterReading = newMeterReading();
        ReadingType readingType = EspiFactory.newReadingType();

        usagePoint.addMeterReading(meterReading);
        meterReading.setReadingType(readingType);

        repository.persist(usagePoint);

        assertNotNull("ReadingType id was null", readingType.getId());
    }

    @Test
    public void findAllUpdatedFor() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);

        UsagePoint usagePoint = newUsagePoint(retailCustomer);
        usagePoint.setUpdated(EspiFactory.newCalendar(2013, 11, 13));
        repository.persist(usagePoint);

        UsagePoint updatedUsagePoint = newUsagePoint(retailCustomer);
        updatedUsagePoint.setUpdated(EspiFactory.newCalendar(2013, 11, 23));
        repository.persist(updatedUsagePoint);

        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        applicationInformationRepository.persist(applicationInformation);

        Subscription subscription = newSubscription(retailCustomer, applicationInformation);
        subscription.setLastUpdate(EspiFactory.newCalendar(2013, 11, 15));
        subscriptionRepository.persist(subscription);

        List<UsagePoint> usagePointList = repository.findAllUpdatedFor(subscription);

        assertThat(usagePointList, hasItem(updatedUsagePoint));
        assertThat(usagePointList, not(hasItem(usagePoint)));
    }

    @Test
    public void findAllUpdatedFor_givenNoUpdatedResources() {
        Subscription subscription = factory.createSubscription();
        List<UsagePoint> usagePointList = repository.findAllUpdatedFor(subscription);

        assertThat(usagePointList, isEmpty());
    }

    @Test
    public void findByRelatedHref() {
        UsagePoint usagePoint = factory.createUsagePoint();
        String relatedLink = UUID.randomUUID().toString();
        usagePoint.getRelatedLinks().add(new LinkType(LinkType.RELATED, relatedLink));
        repository.persist(usagePoint);

        assertThat(repository.findByRelatedHref(relatedLink), equalTo((IdentifiedObject)usagePoint));
    }

    @Test
    public void findAllIdsForRetailCustomer() {
        RetailCustomer retailCustomer = factory.createRetailCustomer();
        UsagePoint up1 = factory.createUsagePoint(retailCustomer);
        UsagePoint up2 = factory.createUsagePoint(retailCustomer);

        List<Long> allIdsForRetailCustomer = repository.findAllIdsForRetailCustomer(retailCustomer.getId());

        assertThat(allIdsForRetailCustomer, allOf(hasItem(up1.getId()), hasItem(up2.getId())));
    }
}
