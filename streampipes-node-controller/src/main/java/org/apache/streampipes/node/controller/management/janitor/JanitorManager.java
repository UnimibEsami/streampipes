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
package org.apache.streampipes.node.controller.management.janitor;

import org.apache.streampipes.node.controller.config.NodeConfiguration;
import org.apache.streampipes.node.controller.management.orchestrator.docker.utils.DockerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JanitorManager {

    private static final Logger LOG =
            LoggerFactory.getLogger(JanitorManager.class.getCanonicalName());

    private static JanitorManager instance = null;

    private JanitorManager() {}

    public static JanitorManager getInstance() {
        if (instance == null) {
            synchronized (JanitorManager.class) {
                if (instance == null)
                    instance = new JanitorManager();
            }
        }
        return instance;
    }

    // regularly clean dangling docker images
    public void run() {
        LOG.debug("Create Janitor scheduler");

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(pruneDocker, 30,
                NodeConfiguration.getDockerPruningFreqSecs(), TimeUnit.MINUTES);
    }

    private final Runnable pruneDocker = () -> {
        LOG.debug("Clean up dangling images");
        DockerUtils.prune();
    };


}