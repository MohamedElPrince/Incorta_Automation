import sys
"""

Fucntion MainWindow shows MainWindow through file_comparitor.py File.

By: Ilyas Reyhanoglu
"""


from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QApplication, QDialog, QWidget, QMainWindow
import file_comparitor

class MainWindow(QMainWindow, file_comparitor.Ui_MainWindow):
	def __init__(self, parent=None):
		super(MainWindow,self).__init__(parent)
		self.setupUi(self)

app = QApplication(sys.argv)
form = MainWindow()
form.show()
app.exec_()