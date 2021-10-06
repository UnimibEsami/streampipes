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
package org.apache.streampipes.performance;

import org.apache.streampipes.logging.evaluation.EvaluationLogger;
import org.apache.streampipes.performance.performancetest.GenericTest;
import org.apache.streampipes.performance.performancetest.Test;

public class TestFactory {


    public static Test getFromEnv(){
        EvaluationLogger logger = EvaluationLogger.getInstance();
        switch (System.getenv("TEST_TYPE")){
            case "Deployment":
                Object[] header_deployment = {"timestampInMillis", "deviceId", "event", "numberOfRuns", "durationInNanos", "durationInSecs"};
                logger.logHeader("Deployment", header_deployment);
                return getDeploymentTest();
            case "Latency":
                return getLatencyTest();
            case "Migration":
                Object[] header_migration = {"timestampInMillis", "deviceId", "event", "numberOfRuns", "durationInNanos", "durationInSecs", "originNode", "targetNode"};
                logger.logHeader("Migration", header_migration);
                return getMigrationTest();
            case "Reconfiguration":
                Object[] header_reconfigure = {"timestampInMillis", "deviceId", "event", "numberOfRuns", "reconfigurationValue"};
                logger.logHeader("Reconfiguration", header_reconfigure);
                return getReconfigurationTest();
            case "Offloading":
                Object[] header_offloading = {"timestampInMillis", "deviceId", "event"};
                logger.logHeader("Offloading", header_offloading);
                return getOffloadingTest();
            default:
                throw new RuntimeException("No test configuration found.");
        }
    }


    public static Test getDeploymentTest(){
        return new GenericTest(getPipelineName(), true, false,
                false, 0, 15000);
    }

    public static Test getLatencyTest(){
        return new GenericTest(getPipelineName(), true, false,
                false, 0, 30000);
    }

    public static Test getMigrationTest(){
        return new GenericTest(getPipelineName(), false, true,
                false, 15000, 7500);
    }

    public static Test getReconfigurationTest(){
        return new GenericTest(getPipelineName(), false, false,
                true, 10000, 5000);
    }

    public static Test getOffloadingTest(){
        return new GenericTest(getPipelineName(), false, false,
                true, 20000, 600000);
    }

    //Helpers
    private static String getPipelineName(){
        String pipelineName = System.getenv("TEST_PIPELINE_NAME");
        if (pipelineName==null) throw new RuntimeException("No Pipeline Name provided.");
        return pipelineName;
    }

}