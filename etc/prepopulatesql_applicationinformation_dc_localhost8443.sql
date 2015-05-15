/* Add application_information */
SET sql_mode = 'PIPES_AS_CONCAT';

SET @dcbaseurl = 'https://localhost:8443';
SET @tpbaseurl = 'https://localhost:8443';

INSERT INTO `application_information` (`id`, `kind`, `description`, `published`, `self_link_href`, `self_link_rel`, `up_link_href`, `up_link_rel`, `updated`, `uuid`, `authorizationServerAuthorizationEndpoint`, `authorizationServerRegistrationEndpoint`, `authorizationServerTokenEndpoint`, `authorizationServerUri`, `clientId`, `clientIdIssuedAt`, `clientName`, `clientSecret`, `clientSecretExpiresAt`, `clientUri`, `contacts`, `dataCustodianApplicationStatus`, `dataCustodianBulkRequestURI`, `dataCustodianDefaultBatchResource`, `dataCustodianDefaultSubscriptionResource`, `dataCustodianId`, `dataCustodianResourceEndpoint`, `dataCustodianThirdPartySelectionScreenURI`, `logoUri`, `policyUri`, `redirectUri`, `registrationAccessToken`, `registrationClientUri`, `responseTypes`, `softwareId`, `softwareVersion`, `thirdPartyApplicationDescription`, `thirdPartyApplicationName`, `thirdPartyApplicationStatus`, `thirdPartyApplicationType`, `thirdPartyApplicationUse`, `thirdPartyDataCustodianSelectionScreenURI`, `thirdPartyLoginScreenURI`, `thirdPartyNotifyUri`, `thirdPartyPhone`, `thirdPartyScopeSelectionScreenURI`, `thirdPartyUserPortalScreenURI`, `tokenEndpointAuthMethod`, `tosUri`, `dataCustodianScopeSelectionScreenURI`) VALUES
(1, 'DATA_CUSTODIAN_ADMIN', 'GreenButtonData.org  Data Custodian Admin Application', '2014-01-02 05:00:00', '/espi/1_1/resource/DataCustodian/ApplicationInformation/1', 'self', '/espi/1_1/resource/DataCustodian/ApplicationInformation', 'up', '2014-01-02 05:00:00', '185C9C3F-5B4A-44A9-8959-3AE79E60A9F5', @dcbaseurl || '/DataCustodian/oauth/authorize', @dcbaseurl || '/DataCustodian/espi/1_1/register', @dcbaseurl || '/DataCustodian/oauth/token', @dcbaseurl || '/DataCustodian/', 'data_custodian', 1403190000, 'Green Button DataCustodian Admin', 'secret', 0, @dcbaseurl || '/DataCustodian','john.teeter@energyos.org,martin.burns@nist.gov,donald.coffin@reminetworks.com', '1', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Bulk', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Subscription', 'data_custodian', @dcbaseurl || '/DataCustodian/espi/1_1/resource', @dcbaseurl || '/DataCustodian/RetailCustomer/{retailCustomerId}/ThirdPartyList', NULL, NULL, NULL, NULL, @dcbaseurl || '/DataCustodian/espi/1_1/resource/ApplicationInformation/1', 1, 'EnergyOS OpenESPI', '1.2', 'EnergyOS OpenESPI Example DataCustodian Admin', 'Data Custodian Admin (localhost)', '1', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, 'client_secret_basic', NULL, @dcbaseurl || '/DataCustodian/RetailCustomer/ScopeSelection'),
(2, 'THIRD_PARTY', 'GreenButtonData.org  ThirdParty Application', '2014-01-02 05:00:00', '/espi/1_1/resource/DataCustodian/ApplicationInformation/2', 'self', '/espi/1_1/resource/DataCustodian/ApplicationInformation', 'up', '2014-01-02 05:00:00', 'AF6E8B03-0299-467E-972A-A883ECDCC575', @dcbaseurl || '/DataCustodian/oauth/authorize', @dcbaseurl || '/DataCustodian/espi/1_1/register', @dcbaseurl || '/DataCustodian/oauth/token', @dcbaseurl || '/DataCustodian/', 'third_party', 1403190000, 'Green Button Third Party', 'secret', 0, @tpbaseurl || '/ThirdParty', 'john.teeter@energyos.org,martin.burns@nist.gov,donald.coffin@reminetworks.com', '1', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Bulk', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Subscription', 'data_custodian', @dcbaseurl || '/DataCustodian/espi/1_1/resource', @dcbaseurl || '/DataCustodian/RetailCustomer/1/ThirdPartyList', @tpbaseurl || '/ThirdParty/resources/ico/favicon.png', NULL, @tpbaseurl || '/ThirdParty/espi/1_1/OAuthCallBack', 'd89bb056-0f02-4d47-9fd2-ec6a19ba8d0c', @dcbaseurl || '/DataCustodian/espi/1_1/resource/ApplicationInformation/2', 1, 'EnergyOS OpenESPI Example Third Party', '1.2', 'EnergyOS OpenESPI Example Third Party', 'Third Party (localhost)', '1', '1', '1', @tpbaseurl || '/ThirdParty/RetailCustomer/{retailCustomerId}/DataCustodianList', @tpbaseurl || '/ThirdParty/login', @tpbaseurl || '/ThirdParty/espi/1_1/Notification', NULL, @tpbaseurl || '/ThirdParty/RetailCustomer/ScopeSelection', @tpbaseurl || '/ThirdParty', 'client_secret_basic', NULL, @dcbaseurl || '/DataCustodian/RetailCustomer/ScopeSelectionList'),
(3, 'UPLOAD_ADMIN', 'GreenButtonData.org  MDM Upload Application', '2014-01-02 05:00:00', '/espi/1_1/resource/DataCustodian/ApplicationInformation/3', 'self', '/espi/1_1/resource/DataCustodian/ApplicationInformation', 'up', '2014-01-02 05:00:00', '185C9C3F-5B4A-44A9-8959-3AE79E60A9F7', @dcbaseurl || '/DataCustodian/oauth/authorize', @dcbaseurl || '/DataCustodian/espi/1_1/register', @dcbaseurl || '/DataCustodian/oauth/token', @dcbaseurl || '/DataCustodian/', 'upload', 1403190000, 'Green Button DMD Upload Service', 'secret', 0, NULL, 'john.teeter@energyos.org,martin.burns@nist.gov,donald.coffin@reminetworks.com', '1', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Bulk', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Subscription', 'data_custodian', @dcbaseurl || '/DataCustodian/espi/1_1/resource', @dcbaseurl || '/DataCustodian/RetailCustomer/1/ThirdPartyList', NULL, NULL, NULL, NULL, @dcbaseurl || '/DataCustodian/espi/1_1/resource/ApplicationInformation/3', 1, 'EnergyOS OpenESPI DMD Upload Service', '1.2', 'EnergyOS OpenESPI DMD Upload Service', 'MDM Upload (localhost)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'client_secret_basic', NULL, NULL);

/* Add application_information_scopes */ 
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=1_3_4_5_13_14_19_32_33_34_35_36_37_38_41_44_45');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_13_14_39;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_13_14_15_39;IntervalDuration=900;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_13_14_39;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_6_7_8_9_10_11_29_12_13_14_15_16_17_18_19_27_28_32_33_34_35_37_38_39_40_41_44;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (3, 'FB=45');

/* Add application_information_grant_types */ 
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (1, 'CLIENT_CREDENTIALS');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (2, 'AUTHORIZATION_CODE');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (2, 'REFRESH_TOKEN');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (2, 'CLIENT_CREDENTIALS');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (3, 'CLIENT_CREDENTIALS');

/* Add predefined authorizations */
INSERT INTO `authorizations` VALUES (1,NULL,'2015-05-14 19:05:08',NULL,'self',NULL,'up','2015-05-14 19:05:08','4F8582F6-D6CC-4FF9-8AFC-F57FB756DB31','9ae38e20-2b90-4218-b679-769277d022ff','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/1',0,0,NULL,NULL,NULL,NULL,1747004707,NULL,0,0,NULL,'https://localhost:8443/DataCustodian/espi/1_1/resource/',NULL,'FB=3_19_32_33_34_35_36_37_38_41_44_45',NULL,'1','data_custodian',0,1,0,1),
(2,NULL,'2015-05-14 19:08:07',NULL,'self',NULL,'up','2015-05-14 19:08:07','4DF33A1C-FF9E-438C-9CEC-BA66701EF333','b4f05e98-0d4a-4477-be85-c30c1a8e5677','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/2',0,0,NULL,NULL,NULL,NULL,1747004886,NULL,0,0,NULL,'https://localhost:8443/DataCustodian/espi/1_1/resource//Batch/RetailCustomer/**/UsagePoint',NULL,'FB=45',NULL,'1','upload',0,3,0,2),
(3,NULL,'2015-05-14 19:08:43',NULL,'self',NULL,'up','2015-05-14 19:08:43','AE8353C9-4C61-4747-A3B8-1D8C07AEB786','ae3db544-c091-443d-8df1-30558965cb43','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/3',0,0,NULL,NULL,NULL,NULL,1747004922,NULL,0,0,NULL,'https://localhost:8443/DataCustodian/espi/1_1/resource//Batch/Bulk/**',NULL,'FB=34_35',NULL,'1','third_party_admin',0,2,0,3),
(4,NULL,'2015-05-14 19:08:48',NULL,'self',NULL,'up','2015-05-14 19:08:48','FAC2B9C3-E28B-474B-B1A1-7D879C1F971E','5be1358f-2d21-46cb-bc09-8404bbb23fb0','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/4',0,0,NULL,NULL,NULL,NULL,1747004927,NULL,0,0,NULL,NULL,NULL,'FB=36_40',NULL,'1','REGISTRATION_third_party',0,2,0,4);

/* Add predefined subscriptions */
INSERT INTO `subscriptions` VALUES (1,NULL,'2015-05-14 19:05:08',NULL,'self',NULL,'up','2015-05-14 19:05:08','1BC72790-1B51-4D20-B411-765626F246CF','c6a3be79-40ea-4978-b7ac-b7a0746d9b0b','2015-05-14 19:05:08',1,1,0),
(2,NULL,'2015-05-14 19:08:07',NULL,'self',NULL,'up','2015-05-14 19:08:07','231CA4EC-B6B2-40F0-B77B-A8E9C4088B81','632f74f9-7b2a-4c2c-a252-b692f5d5481f','2015-05-14 19:08:07',3,2,0),
(3,NULL,'2015-05-14 19:08:43',NULL,'self',NULL,'up','2015-05-14 19:08:43','417D40FA-BE09-4DF4-A5DC-331E2B282F55','d076081f-884b-4a4f-8d7a-5ffe5c4919a6','2015-05-14 19:08:43',2,3,0),
(4,NULL,'2015-05-14 19:08:48',NULL,'self',NULL,'up','2015-05-14 19:08:48','77D7CCF9-6331-49F4-8D2D-FE2CBBA05707','54a2e420-ccce-425e-9cd0-9cdeaf0d0826','2015-05-14 19:08:48',2,4,0);
