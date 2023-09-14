//
import Foundation

func display(_ file: UnsafeMutablePointer<FILE>) {
    var buffer = [CChar](repeating: 0, count: 1024)
      while fgets(&buffer, Int32(buffer.count), file) != nil {
        let currentLine = String(cString: buffer)
        print(currentLine, terminator: "")
    }
}

func solve(_ filePath: String, _ number: Int) -> Void{
    let filePath = filePath.trimmingCharacters(in: .whitespacesAndNewlines)
    let fileManager = FileManager.default
    if fileManager.fileExists(atPath: filePath) {
       guard let file = fopen(filePath, "r") else {
            perror("Error opening file")
            return
        }

        defer {
            fclose(file)
        }

        var count = -1;
        fseek(file, -1, SEEK_END)
        var fileSize = ftell(file)

        while fileSize >= 0{
            fseek(file, fileSize, SEEK_SET)
            if fgetc(file) == 10 {
                count += 1
            }
            if count == number {
                fseek(file, fileSize + 1, SEEK_SET)
                display(file)
                break
            }
            fileSize -= 1
        }

        if fileSize == -1 {
            fseek(file, 0, SEEK_SET)
            display(file)
        }

    }
    else {
        print("FIle NOT EXISTS")
    }
}

if CommandLine.arguments.count > 2 {
    let filePath = String(CommandLine.arguments[1])
    let number = Int(CommandLine.arguments[2])
    if(number! < 0) {
        print("Inavlid number")
    } else {
        solve(filePath, number!)
    }
} else {
    print("Insuffcient number of arguments")
}
