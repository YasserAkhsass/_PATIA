import matplotlib.pyplot as plt
import numpy as np

# Data
sat_data = [1738, 1822, 1906, 3323, 3026, 245651, 5342, 2175, 2227, 2839, 22520, 322473, 8141, 50617, 16843]
asp_data = [1293, 2252, 1908, 3458, 2122, 224708, 4714, 2480, 2880, 2180, 12446, 293675, 7820, 46465, 14390]


# X-axis values
x = np.arange(len(sat_data))

# Plotting the data
plt.bar(x, sat_data, width=0.4, color='b', align='center', label='SAT Planner') 
plt.bar(x + 0.4, asp_data, width=0.4, color='g', align='center', label='ASP Planner')

# Labels
plt.xlabel('Problem Instance Number')
plt.ylabel('Execution Time (ms)')
plt.title('SAT Planner vs ASP Planner on Logistics Domain')

# X-axis ticks
plt.xticks(x + 0.2, [f'p{i+1:02}' for i in range(len(sat_data))])

# Legend
plt.legend()

# Display the plot
plt.show()
