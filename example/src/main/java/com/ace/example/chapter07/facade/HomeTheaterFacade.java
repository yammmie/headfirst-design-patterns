package com.ace.example.chapter07.facade;

import com.ace.example.chapter07.facade.system.Amplifier;
import com.ace.example.chapter07.facade.system.PopcornPopper;
import com.ace.example.chapter07.facade.system.Projector;
import com.ace.example.chapter07.facade.system.Screen;
import com.ace.example.chapter07.facade.system.StreamingPlayer;
import com.ace.example.chapter07.facade.system.TheaterLights;
import com.ace.example.chapter07.facade.system.Tuner;

public class HomeTheaterFacade {

  Amplifier amp;
  Tuner tuner;
  StreamingPlayer player;
  Projector projector;
  TheaterLights lights;
  Screen screen;
  PopcornPopper popper;

  public HomeTheaterFacade(
      Amplifier amp,
      Tuner tuner,
      StreamingPlayer player,
      Projector projector,
      TheaterLights lights,
      Screen screen,
      PopcornPopper popper
  ) {
    this.amp = amp;
    this.tuner = tuner;
    this.player = player;
    this.projector = projector;
    this.lights = lights;
    this.screen = screen;
    this.popper = popper;
  }

  public void watchMovie(String movie) {
    System.out.println("영화 볼 준비 중");
    popper.on();
    popper.pop();
    lights.dim(10);
    screen.down();
    projector.on();
    projector.wideScreenMode();
    amp.on();
    amp.setStreamingPlayer(player);
    amp.setSurroundSound();
    amp.setVolume(5);
    player.on();
    player.play(movie);
  }

  public void endMovie() {
    System.out.println("홈시어터를 끄는 중");
    popper.off();
    lights.on();
    screen.up();
    projector.off();
    amp.off();
    player.stop();
    player.off();
  }

}
