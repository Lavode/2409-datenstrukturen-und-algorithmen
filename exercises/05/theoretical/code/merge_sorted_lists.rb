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

      node.next = @head
    end

    def dequeue
      return nil unless @head

      ret = @head
      @head = ret.next
      ret.next = nil

      return ret
    end

    def smallest
      node = @head
      while (node = node.next) && (node != @head) do
        # As the list is sorted, any drop in key value means that we've arrived
        # at the very smallest element.
        return node if node.key < @head.key
      end

      # Either all nodes were equal, or the head was the smallest already.
      return node
    end

    def to_s
      out = []
      out << '['
      if @head
        first_node = node = @head
        begin
          out << '[HEAD]' if node == @head
          out << node.to_s
          out << '[TAIL]' if node == @tail
          out << ', ' if node.next
        end while (node = node.next) && (node != first_node)
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

queue1 = Test::Queue.new
queue2 = Test::Queue.new

10.times.map { (1..9).to_a.sample }.sort.each do |i|
  queue1.enqueue(i)
end

values = 10.times.map { (1..9).to_a.sample }.sort
values = values[values.length/2..-1] + values[0...values.length/2]
# values = [3] * 10
values.each do |i|
  queue2.enqueue(i)
end
# [1, 3, 5].each { |i| queue2.enqueue(i) }
# [2, 2, 6].each { |i| queue1.enqueue(i) }

puts queue1
smallest1 = queue1.smallest
puts "Smallest element: #{ smallest1 }"
puts ""
puts queue2
smallest2 = queue2.smallest
puts "Smallest element: #{ smallest2 }"
puts ''


def merge(node1, node2)
  initial_node1 = node1
  initial_node2 = node2

  node1_moved = false
  node2_moved = false

  head = Test::Node.new(nil)
  prev = head

  while true do
    if node1.key < node2.key
      prev.next = node1
      prev = node1
      node1 = node1.next
      node1_moved = true
    else
      prev.next = node2
      prev = node2
      node2 = node2.next
      node2_moved = true
    end

    if node1 == initial_node1 && node1_moved
      puts 'Reached end of list 1'
      prev.next = node2
      prev = node2
      while node2.next != initial_node2
        node2 = node2.next
      end
      # Bypass empty node at start of queue
      node2.next = head.next
      break
    elsif node2 == initial_node2 && node2_moved
      puts 'Reached end of list 2'
      prev.next = node1
      prev = node1
      while node1.next != initial_node1
        node1 = node1.next
      end
      # Bypass empty node at start of queue
      node1.next = head.next
      break
    end
  end

  return head.next
end


head = merge(smallest1, smallest2)
node = head
begin
  puts node.to_s
end while (node = node.next) && (node != head)
