package _base;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static _base.Util.*;

record InputFile(Optional<DataFiles.Root> root, String label, Path path, List<String> zipSteps, String zipEntry) implements CapInputCursorNode$8sg8$0{
  InputFile(Path path){ this(Optional.empty(), portable(path.getFileName().toString()), path, List.of(), ""); }
  static String portable(String name){
    return name.codePoints()
      .map(c -> UStr$s$0Instance.isStrScalar(c) ? c : '?')
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$label$0(){ return Str$c$0Instance.instance(label); }
  //TODO: we will need to have System or something have some callback to do masking on the paths if the user wants to prevent the program to see OS paths
  String subject(){ return root.isPresent() ? "Resource with path "+label : "File with absolute path "+portable(path.toAbsolutePath().toString()); }
  byte[] bytes(){
    root.ifPresent(DataFiles::reachable);
    try{ return DataFiles.bytes(path, zipSteps, zipEntry); }
    catch (IOException _){ throw nonDetErr(subject()+"\ncan not be read as bytes"); }
  }
  @Override public Object mut$readStr$1(Object cleaner){ return TextCleaning.str(cleaner, bytes()); }
  @Override public Object mut$readUStr$1(Object cleaner){ return TextCleaning.uStr(cleaner, bytes()); }
  @Override public Object mut$readBytes$0(){
    var bs= bytes();
    var l= new java.util.ArrayList<Object>(bs.length);
    for (var b : bs){ l.add(Byte$o$0Instance.instance(b)); }
    return List$o$1Instance.wrap(l);
  }
  @Override public Object mut$readImage$1(Object maxPixels){
    return new Image$1c$0Instance(AssetImageRead.decode(subject(), bytes(), AssetBytesRead.nat(maxPixels)));
  }
}
