package admin.team;

import admin.team.renderers.TeamRenderer;
import admin.team.workers.RemoveMemberWorker;
import admin.team.workers.SeeMembersWorker;
import run.JDialoger;
import utils.MainPanel;
import utils.TooltipButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TeamTable extends MainPanel {

	JPanel basePanel;

	JPanel northPanel;

	TooltipButton seeAllUserButton;
	TooltipButton addMemberButton;
	TooltipButton removeMemberButton;
	TooltipButton editMemberButton;

	JPanel centerPanel;
	JTable table;
	DefaultTableModel dftm;
	JScrollPane scroll;
	int SELECTED_ROW = -1;

	public TeamTable() {
		basePanel = new JPanel();
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		basePanel.setLayout(new BorderLayout());

		northPanel = new JPanel();// GradientPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		seeAllUserButton = new TooltipButton("Виж всички потребители");
		seeAllUserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (dftm.getRowCount() > 0) {
					dftm.setRowCount(0);
				}
				JDialog jd = (JDialog) SwingUtilities
						.getWindowAncestor(TeamTable.this);
				jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				SeeMembersWorker see = new SeeMembersWorker(jd);
				try {
					ArrayList<Object[]> result = see.doInBackground();
					if (result.size() > 0) {
						for (Object[] objects : result) {
							Object[] obj = new Object[objects.length];
							for (int j = 0; j < objects.length; j++) {
								if (j < 2) {
									obj[j] = objects[j];
								} else {
									if (objects[j].equals("yes")) {
										obj[j] = Boolean.TRUE;
									} else {
										obj[j] = Boolean.FALSE;
									}
								}
							}
							// System.out.println();
							dftm.addRow(obj);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		addMemberButton = new TooltipButton("Добави нов потребител");

		addMemberButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AddUserDialog addUser = new AddUserDialog();

				final JDialoger jDialog = new JDialoger();
				jDialog.setContentPane(addUser);
				jDialog.setTitle("Добави нов потребител");
				jDialog.setResizable(false);
				jDialog.Show();
			}

		});

		editMemberButton = new TooltipButton("Редактирай потребител");

		removeMemberButton = new TooltipButton("Изтрий потребител");
		removeMemberButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (SELECTED_ROW == -1) {
					return;
				}
				int yes_no = JOptionPane
						.showOptionDialog(
								null,
								"Сигурни ли сте че искате да изтриете този потребител ?",
								"", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null,
								new String[] { "Да", "Не" }, "default");
				if (yes_no == 0) {
					JDialog jd = (JDialog) SwingUtilities
							.getWindowAncestor(TeamTable.this);
					jd.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					RemoveMemberWorker remove = new RemoveMemberWorker(table
							.getValueAt(SELECTED_ROW, 0).toString(), jd);
					remove.execute();
				}
			}

		});
		northPanel.add(seeAllUserButton);
		northPanel.add(addMemberButton);
		// northPanel.add(editMemberButton);
		northPanel.add(removeMemberButton);

		centerPanel = new JPanel();

		dftm = new DefaultTableModel(new String[] { "Потребител", "Парола",
				"Сервизна поръчка", "Работна книга", "Фактури", "Справки",
				"Нов пожарогасител", "Скрито меню", "Стокова Разписка" }, 0) {
			@Override
			public Class getColumnClass(int column) {
				if (column > 1) {
					return Boolean.class;
				}
				return String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(dftm);
		table.setDefaultRenderer(Boolean.class, new TeamRenderer(getFontSize()));
		table.setRowHeight(MainPanel.getFontSize() + 15);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				SELECTED_ROW = table.getSelectedRow();
			}
		});

		scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT - 70));
		centerPanel.add(scroll);

		basePanel.add(northPanel, BorderLayout.NORTH);
		basePanel.add(centerPanel, BorderLayout.CENTER);

		this.add(basePanel);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JDialoger jDialoger = new JDialoger();
				TeamTable t = new TeamTable();
				jDialoger.setContentPane(t);
				jDialoger.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				jDialoger.Show();

			}

		});
	}

}
