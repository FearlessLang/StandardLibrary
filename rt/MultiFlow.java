package base;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public abstract class MultiFlow {
  protected interface Filler {
    Object fromIndex(Long unsignedIndex);
  }
  final Iterator<Object>[] channels;
  final Filler[] fillers; // can be null
  final int numChannels;

  long unsignedIndex = 0;

  protected MultiFlow(Iterator<Object>[] channels) {
    this.channels = channels;
    this.numChannels = channels.length;
    this.fillers = new Filler[channels.length];
  }
  protected MultiFlow(MultiFlow flow1, MultiFlow flow2) {
    this.numChannels = flow1.numChannels + flow2.numChannels;
    this.channels = Arrays.copyOf(flow1.channels, numChannels);
    System.arraycopy(flow2.channels, 0, this.channels, flow1.numChannels, flow2.numChannels);

    this.fillers = Arrays.copyOf(flow1.fillers, numChannels);
    System.arraycopy(flow2.fillers, 0, this.fillers, flow1.numChannels, flow2.numChannels);
  }

  protected void fillChannel(int channelIndex, Filler channelFiller) {
    fillers[channelIndex] = channelFiller;
  }
  boolean hasNext() {
    return IntStream.rangeClosed(0, numChannels)
      .allMatch(i -> Objects.nonNull(fillers[i]) || channels[i].hasNext());
  }

  Object[] next() {
    Object[] data = IntStream.range(0, numChannels)
      .mapToObj(i -> {
        if (channels[i].hasNext()) {
          return channels[i].next();
        }
        if (Objects.nonNull(fillers[i])) {
          return fillers[i].fromIndex(this.unsignedIndex);
        }
        throw new NoSuchElementException("Iterator exhausted");
      }).toArray();
    this.unsignedIndex+=1;
    return data;
  }

  protected Stream<Object> zipExact(Function<Object[], T> merger) {
    var self = this;

    Iterator<Object> iter = new Iterator<>() {
      @Override
      public boolean hasNext() {
        return self.hasNext();
      }

      @Override
      public Object next() {
        return merger.apply(self.next());
      }
    };

    return StreamSupport.stream(
      Spliterators.spliteratorUnknownSize(iter, Spliterator.ORDERED),
      false
    );
  }
}


