/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.streampipes.node.controller.management.offloading.strategies;

import org.apache.streampipes.node.controller.management.offloading.strategies.policies.OffloadingPolicy;
import org.apache.streampipes.node.controller.management.offloading.strategies.property.ResourceProperty;
import org.apache.streampipes.node.controller.management.offloading.strategies.selection.SelectionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OffloadingStrategy<T> {
    private SelectionStrategy selectionStrategy;
    private OffloadingPolicy<T> offloadingPolicy;
    private ResourceProperty<T> resourceProperty;
    private static final Logger LOG = LoggerFactory.getLogger(OffloadingStrategy.class.getCanonicalName());

    public OffloadingStrategy(OffloadingPolicy offloadingPolicy, ResourceProperty resourceProperty,
                              SelectionStrategy selectionStrategy){
        this.offloadingPolicy = offloadingPolicy;
        this.resourceProperty = resourceProperty;
        this.selectionStrategy = selectionStrategy;
        LOG.info("Registered offloading strategy: "
                + this.selectionStrategy.getClass().getSimpleName() + " | "
                + this.resourceProperty.getClass().getSimpleName() + " | "
                + this.offloadingPolicy.getClass().getSimpleName());
    }

    public SelectionStrategy getSelectionStrategy() {
        return selectionStrategy;
    }

    public void setSelectionStrategy(SelectionStrategy selectionStrategy) {
        this.selectionStrategy = selectionStrategy;
    }

    public OffloadingPolicy<?> getOffloadingPolicy() {
        return offloadingPolicy;
    }

    public void setOffloadingPolicy(OffloadingPolicy<T> offloadingPolicy) {
        this.offloadingPolicy = offloadingPolicy;
    }

    public ResourceProperty<?> getResourceProperty() {
        return resourceProperty;
    }

    public void setResourceProperty(ResourceProperty<T> resourceProperty) {
        this.resourceProperty = resourceProperty;
    }

}