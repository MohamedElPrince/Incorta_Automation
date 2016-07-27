import codecs
import json


def getContentFromFile(filePath):
    return open(filePath, 'r').read()

def compare(file1, file2):
    newJsonFile = open(file1)
    lastJsonFile = open(file2)
    newLines = newJsonFile.readlines()
    oldLines = lastJsonFile.readlines()
    print newLines
    print oldLines
    sortedNew = sorted([repr(x) for x in newJsonFile])
    sortedLast = sorted([repr(x) for x in lastJsonFile])
    print sortedNew
    print sortedLast
    return (sortedNew == sortedLast)

def ordered(obj):
    if isinstance(obj, dict):
        return sorted((k, ordered(v)) for k, v in obj.items())
    if isinstance(obj, list):
        return sorted(ordered(x) for x in obj)
    else:
        return obj


file1 = '/Users/anahit/desktop/admin/98e77650-bd45-45dd-b577-b447a781f8c0.json'
file2 = '/Users/anahit/desktop/admin-changed/98e77650-bd45-45dd-b577-b447a781f8c0.json'
result = compare(file1, file2)
print result

with open(file1) as data_file:
    data = json.load(data_file)
with open(file2) as data_file:
    data2 = json.load(data_file)

result1 = ordered(data)
result2 = ordered(data2)

print result1
print result2
print ordered(data) == ordered(data2)
# if (ordered(data) == ordered(data2)) == True:
#     print True
# else:
#     print False
