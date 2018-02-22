sample_sizes = c(10000, 30000, 50000, 100000, 300000, 500000)
insert_sort_times = c(0.026, 0.145, 0.372, 1.511, 14.379, 44.486)
merge_sort_times = c(0.003, 0.005, 0.008, 0.017, 0.053, 0.071)

plot(sample_sizes, insert_sort_times, main = "Execution time insert-sort", xlab = "Sample size", ylab = "Time [s]")
plot(sample_sizes, merge_sort_times, main = "Execution time merge-sort", xlab = "Sample size", ylab = "Time [s]")