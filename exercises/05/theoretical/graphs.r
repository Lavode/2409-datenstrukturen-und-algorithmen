elements = c(1000, 2500, 10000, 25000, 50000, 100000)

tree_build_time = c(17, 38, 306, 2119, 7221, 26462)
list_search_time = c(20.6, 44.4, 119.6, 256.2, 507.8, 1911.2)
tree_search_time = c(2.8, 3.0, 3.4, 3.4, 3.8, 5.2)

matplot(tree_build_time, x = elements, type=c("b"), pch = 1, ylab = "Time [ms]", xlab = "Input size", col = 1:4)
legend("top", legend = c("Tree build"), bty = "n")

matplot(list_search_time, x = elements, type=c("b"), pch = 1, ylab = "Time [ms]", xlab = "Input size", col = 1:4)
legend("top", legend = c("List search"), bty = "n")

matplot(tree_search_time, x = elements, type=c("b"), pch = 1, ylab = "Time [ms]", xlab = "Input size", col = 1:4)
legend("top", legend = c("Tree search"), bty = "n")
