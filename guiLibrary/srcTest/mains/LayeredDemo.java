package mains;

import base.*;
import static mains.Scopes.*;

public class LayeredDemo{
  static final Scope$1 button=new Scope$1(){
    @Override public Object mut$run$1(Object p0){
      var b=(Button$0)p0;
      b.mut$height$1(h(40));
      b.mut$width$1(w(100));
      b.mut$radius$1(n(20));
      b.mut$background$1(boringGray());
      b.mut$foreground$1(black());
      b.mut$action$1(new MF$1(){
        @Override public Object mut$$hash$0(){
          throw new RuntimeException("Boom");
        }
      });
      return b;
    }
  };

  public static void main(String[] a){
    new _FluentGUI().mut$run$1(new Consumer$1(){
      @Override public Object mut$accept$1(Object p0){
        var gui=(Frame$0)p0;
        gui.mut$contentB$1(new Scope$1(){
          @Override public Object mut$run$1(Object p0){
            var p=(Border$0)p0;
            p.mut$background$1(bananaBruise());
            p.mut$south$1(new Scope$1(){
              @Override public Object mut$run$1(Object p0){
                var s=(Pane$0)p0;
                s.mut$button$1(namedButton("Start"));
                s.mut$button$1(namedButton("Pause"));
                s.mut$button$1(namedButton("Stop"));
                return s;
              }
            });
            return p;
          }
        });
        return Void$0.instance;
      }
    });
  }

  static Scope$1 namedButton(String text){
    return new Scope$1(){
      @Override public Object mut$run$1(Object p0){
        var b=(Button$0)p0;
        button.mut$run$1(b);
        b.mut$text$1(new Str$0Instance(text));
        return b;
      }
    };
  }
}