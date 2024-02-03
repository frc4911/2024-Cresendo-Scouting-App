import os
import pandas as pd



# ADB DRIVER, PANDAS, OS REQUIRED
# One device at a time
# enable debug mode on the device

device_path = '/storage/emulated/0'
local_path = './csv_files' 
current_directory = os.getcwd()
os.chdir(current_directory)


devices_output = os.popen("adb devices").read()
print("Connected devices:\n", devices_output)

os.makedirs(local_path, exist_ok=True)

# 获取设备上的所有CSV文件列表
csv_files = os.popen(f"adb shell ls {device_path}/*.csv").read().strip().split('\n')

for file in csv_files:
    if file: 
        file = file.replace('\r', '')
        adb_pull_command = f"adb pull {file} {local_path}/"
        print(f"Running: {adb_pull_command}")
        os.system(adb_pull_command)

print("All CSV files have been downloaded \n")

input("To Continue to Merge data")


def read_csv_flexibly(file_path):
    try:
        # Dynamically determine the max number of columns
        with open(file_path, 'r', encoding='utf-8') as file:
            lines = file.readlines()
        max_columns = max(len(line.split(',')) for line in lines)
        # Read the file with a flexible number of columns
        return pd.read_csv(file_path, header=None, names=range(max_columns))
    except Exception as e:
        print(f"Error reading {file_path}: {e}")
        return pd.DataFrame()  # Return an empty DataFrame in case of error

def get_csv_filenames(directory):
    # Use a list comprehension for conciseness
    return [file for file in os.listdir(directory) if file.endswith(".csv")]

combined_df = pd.DataFrame()

for file_ in get_csv_filenames(local_path):
    df = read_csv_flexibly(f'{local_path}/{file_}')
    combined_df = pd.concat([combined_df, df], ignore_index=True)
    print(f"combined {file_}")
# Check if Main.csv exists and either create it or append to it
main_csv_path = './Main.csv'
if not os.path.exists(main_csv_path):
    combined_df.to_csv(main_csv_path, index=False)
else:
    main_df = pd.read_csv(main_csv_path, header=None, names=range(combined_df.shape[1]))  # Adjust column names to match combined_df
    main_df = pd.concat([main_df, combined_df], ignore_index=True)
    main_df.to_csv(main_csv_path, index=False)
print("Combined data has been saved \n")
# Ask the user if they want to delete the merged data
delete_choice = input("Do you want to delete the merged data from both Kindle and the local path? (y/n): ").strip().lower()

if delete_choice == 'y':
    # Delete the merged data from Kindle
    for file in csv_files:
        if file:
            adb_delete_command = f"adb shell rm {file}"
            print(f"Running: {adb_delete_command}")
            os.system(adb_delete_command)
    
    # Delete the merged data from the local path
    for file_ in get_csv_filenames(local_path):
        file_path = os.path.join(local_path, file_)
        os.remove(file_path)
        print(file_path)
    print("Merged data has been deleted from both Kindle and the local path.")
else:
    print("Merged data has not been deleted.")
