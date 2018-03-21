elements = c(10000, 20000, 40000, 80000, 160000, 320000)

old_d25 = c(12, 13, 29, 101, 294, 810)
old_d50 = c(19, 25, 83, 229, 733, 1791)
old_d150 = c(52, 98, 414, 1277, 3300, 7988)
old_d300 = c(103, 221, 1156, 3329, 7715, 16981)
old_data = matrix(c(old_d25, old_d50, old_d150, old_d300), nrow = 6, ncol = 4)

new_d25 = c(20, 16, 33, 93, 258, 685)
new_d50 = c(26, 29, 73, 209, 569, 1386)
new_d150 = c(64, 94, 273, 758, 1784, 4177)
new_d300 = c(110, 197, 613, 1646, 3843, 9060)
new_data = matrix(c(new_d25, new_d50, new_d150, new_d300), nrow = 6, ncol = 4)

matplot(old_data, type = c("b"), pch = 1, x = elements, ylab = "Time [ms]", xlab = "Input size", col = 1:4)
legend("topleft", legend = c("d = 25", "d = 50", "d = 150", "d = 300"), pch = 1, col = 1:4)
legend("top", legend = c("Old Radix-sort"), bty = "n")

matplot(new_data, type = c("b"), pch = 1, x = elements, ylab = "Time [ms]", xlab = "Input size", col = 1:4)
legend("topleft", legend = c("d = 25", "d = 50", "d = 150", "d = 300"), pch = 1, col = 1:4)
legend("top", legend = c("New Radix-sort"), bty = "n")