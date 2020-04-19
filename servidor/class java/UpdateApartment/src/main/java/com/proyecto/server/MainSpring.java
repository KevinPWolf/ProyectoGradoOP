/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.proyecto.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:META-INF/spring/applicationContext.xml"})
public class MainSpring {

//http://localhost:8080/proyecto/vr/apartmentUpdate
	//http://localhost:8080/proyecto/vr/apartmentUpdate/Delete
	//http://localhost:8080/proyecto/vr/apartmentUpdate/DeleteFavorito
	//http://localhost:8080/proyecto/vr/apartmentUpdate/Forgot
	//http://localhost:8080/proyecto/vr/apartmentUpdate/Change
//https://update-vr.herokuapp.com/proyecto/vr/apartmentUpdate
	public static void main(String[] args) {
        SpringApplication.run(MainSpring.class, args);
    }
}
