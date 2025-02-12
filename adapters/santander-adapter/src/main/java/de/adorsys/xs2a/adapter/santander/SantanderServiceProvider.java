/*
 * Copyright 2018-2021 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.Oauth2ServiceProvider;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.AbstractAdapterServiceProvider;

public class SantanderServiceProvider extends AbstractAdapterServiceProvider implements Oauth2ServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        return new SantanderAccountInformationService(aspsp,
            getOauth2Service(aspsp, httpClientFactory),
            httpClientFactory,
            linksRewriter);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {
        return new SantanderPaymentInitiationService(aspsp,
            httpClientFactory,
            linksRewriter,
            getOauth2Service(aspsp, httpClientFactory));
    }

    @Override
    public String getAdapterId() {
        return "santander-adapter";
    }

    @Override
    public SantanderOauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        return SantanderOauth2Service.create(aspsp, httpClientFactory);
    }
}
