package com.codurance.lsp;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class VehicleShould {
  @Test
  public void start_engine() {
    Vehicle vehicle = new TestableVehicle();

    vehicle.startEngine();

    assertThat(vehicle.engineIsStarted())
      .isTrue();

  }

  @Test
  public void stop_engine() {
    Vehicle vehicle = new TestableVehicle();

    vehicle.startEngine();
    vehicle.stopEngine();

    assertThat(vehicle.engineIsStarted())
      .isFalse();
  }


  public class TestableVehicle extends Vehicle{

    @Override
    public void fill() {

    }

    
  }

}