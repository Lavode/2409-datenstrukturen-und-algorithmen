module Test
  class Queue
    def initialize
      @head = nil
      @tail = nil
    end

    def enqueue(val)
      node = Node.new(val)

      @tail.next = node if @tail
      @tail = node
      @head = node unless @head
    end

    def dequeue
      return nil unless @head

      ret = @head
      @head = ret.next
      ret.next = nil

      return ret
    end

    def to_s
      out = []
      out << '['
      if @head
        node = @head
        begin
          out << '[HEAD]' if node == @head
          out << node.to_s
          out << '[TAIL]' if node == @tail
          out << ', ' if node.next
        end while node = node.next
      end
      out << ']'

      out.join('')
    end
  end

  class Node
    attr_accessor :next
    attr_reader :key

    def initialize(key)
      @key = key
    end

    def to_s
      "<#{ @key }->#{ @next ? @next.key : 'nil' }>"
    end
  end
end

queue = Test::Queue.new

cmds = [
  [:e, 3],
  [:e, 5],
  [:d],
  [:e, 2],
  [:d],
  [:e, 8],
  [:e, 9],
  [:d],
  [:d],
  [:d],
  [:d],
]

cmds.each do |ary|
  if ary.first == :e
    queue.enqueue(ary.last)
    puts "Enqueueing #{ ary.last }"
    puts "Queue is now: #{ queue }"
  else
    res = queue.dequeue
    puts "Dequeued: #{ res ? res.key : 'nil' }"
    puts "Queue is now: #{ queue }"
  end
  puts "\n"
end
