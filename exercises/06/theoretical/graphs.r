elements = c(10, 20, 40, 80, 160, 320, 640)

balls_100 = log10(c(0.04, 0.04, 0.07, 0.13, 0.43, 1.36, 4.9))
balls_1000 = log10(c(0.69, 0.46, 0.39, 0.36, 0.68, 1.62, 4.21))
balls_10000 = log10(c(66.64, 22.29, 8.19, 5.75, 8.61, 7.9, 12.19))


data = matrix(c(balls_100, balls_1000, balls_10000), nrow = 7, ncol = 3)

matplot(data, type = c("b"), pch = 1, x = elements, ylab = "log10 Time [ms]", xlab = "sqrt Hash Table Size", col = 1:4)
legend("topleft", legend = c("100 balls", "1000 balls", "10000 balls"), pch = 1, col = 1:4)
legend("top", legend = c("Collision Detection"), bty = "n")
