package admin.team;

import admin.team.renderers.TeamRenderer;
import admin.team.workers.RemoveMemberWorker;
import admin.team.workers.SeeMembersWorker;
import http.RequestCallback;
import http.RequestCallback2;
import http.user.GetUserService;
import models.User;
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
import java.util.List;

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

				GetUserService service = new GetUserService();
				service.getUsers(new RequestCallback() {
					@Override
					public <T> void callback(List<T> objects) {

						jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

						if(objects != null) {
							List<User> users = (List<User>) objects;
							for (User user : users) {

								Object[] obj = {user.getUsser(), user.getPassword(),user.getService_Order().equals("yes")
										? Boolean.TRUE : Boolean.FALSE,
								        user.getWorking_Book().equals("yes")
										? Boolean.TRUE : Boolean.FALSE, user.getInvoice().equals("yes")
										? Boolean.TRUE : Boolean.FALSE, user.getReports().equals("yes")
										? Boolean.TRUE : Boolean.FALSE,
								        user.getNew_Ext().equals("yes")
										? Boolean.TRUE : Boolean.FALSE, user.getHidden_Menu().equals("yes")
										? Boolean.TRUE : Boolean.FALSE, user.getAcquittance().equals("yes")
										? Boolean.TRUE : Boolean.FALSE};

								dftm.addRow(obj);
							}
						}
					}
				});

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

					GetUserService service = new GetUserService();
					service.deleteUser(table.getValueAt(SELECTED_ROW, 0).toString(), new RequestCallback2() {
						@Override
						public <T> void callback(T t) {

							jd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

							Integer result = (Integer) t;
							if(result > 0) {
								JOptionPane.showMessageDialog(null, "Потребителят е изтрит!");
							}
						}
					});
//					RemoveMemberWorker remove = new RemoveMemberWorker(table
//							.getValueAt(SELECTED_ROW, 0).toString(), jd);
//					remove.execute();
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
