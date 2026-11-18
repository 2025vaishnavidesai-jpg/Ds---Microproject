import java.awt.*;
import java.awt.event.*;

class Node
{
    int coeff, exp;
    Node next;

    Node(int c, int e)
    {
        coeff = c;
        exp = e;
        next = null;
    }
}

class PolynomialAWT extends Frame implements ActionListener
{
    Label l1, l2;
    TextField t1, t2;
    TextArea ta;
    Button b1, b2, b3, b4, b5, b6;

    Node p1 = null, p2 = null, result = null;

    PolynomialAWT()
    {
        setTitle("Polynomial Addition using Linked List");
        setSize(550, 500);
        setLayout(new FlowLayout());

        l1 = new Label("Enter First Polynomial (coeff exponent):");
        t1 = new TextField(35);

        l2 = new Label("Enter Second Polynomial (coeff exponent):");
        t2 = new TextField(35);

        b1 = new Button("Create P1");
        b2 = new Button("Create P2");
        b3 = new Button("Display P1");
        b4 = new Button("Display P2");
        b5 = new Button("Add");
        b6 = new Button("Clear");

        ta = new TextArea(15, 55);

        add(l1);
        add(t1);
        add(b1);

        add(l2);
        add(t2);
        add(b2);

        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(ta);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });

        setVisible(true);
    }

    // Create Polynomial using Linked List

    Node create(String s)
    {
        if(s.trim().isEmpty())
            throw new IllegalArgumentException("Input cannot be empty!");

        Node head = null, temp = null;

        String a[] = s.split(",");

        for(int i = 0; i < a.length; i++)
        {
            String p[] = a[i].trim().split("\\s+");

            if(p.length != 2)
                throw new IllegalArgumentException(
                    "Enter coefficient and exponent!"
                );

            int c = Integer.parseInt(p[0]);
            int e = Integer.parseInt(p[1]);

            if(e < 0)
                throw new IllegalArgumentException(
                    "Exponent cannot be negative!"
                );

            Node newnode = new Node(c, e);

            if(head == null)
            {
                head = newnode;
                temp = newnode;
            }
            else
            {
                if(e >= temp.exp)
                    throw new IllegalArgumentException(
                        "Enter exponents in descending order!"
                    );

                temp.next = newnode;
                temp = newnode;
            }
        }

        return head;
    }

    // Display Polynomial

    void display(Node head)
    {
        if(head == null)
        {
            ta.append("Polynomial is empty!\n");
            return;
        }

        Node temp = head;

        while(temp != null)
        {
            ta.append(temp.coeff + "x^" + temp.exp);

            if(temp.next != null)
                ta.append(" + ");

            temp = temp.next;
        }

        ta.append("\n");
    }

    // Add Two Polynomials

    Node add(Node p, Node q)
    {
        Node head = null, temp = null;

        while(p != null && q != null)
        {
            int c, e;

            if(p.exp == q.exp)
            {
                c = p.coeff + q.coeff;
                e = p.exp;

                p = p.next;
                q = q.next;
            }
            else if(p.exp > q.exp)
            {
                c = p.coeff;
                e = p.exp;

                p = p.next;
            }
            else
            {
                c = q.coeff;
                e = q.exp;

                q = q.next;
            }

            if(c != 0)
            {
                Node newnode = new Node(c, e);

                if(head == null)
                {
                    head = newnode;
                    temp = newnode;
                }
                else
                {
                    temp.next = newnode;
                    temp = newnode;
                }
            }
        }

        while(p != null)
        {
            Node newnode = new Node(p.coeff, p.exp);

            if(head == null)
            {
                head = newnode;
                temp = newnode;
            }
            else
            {
                temp.next = newnode;
                temp = newnode;
            }

            p = p.next;
        }

        while(q != null)
        {
            Node newnode = new Node(q.coeff, q.exp);

            if(head == null)
            {
                head = newnode;
                temp = newnode;
            }
            else
            {
                temp.next = newnode;
                temp = newnode;
            }

            q = q.next;
        }

        return head;
    }

    // Handle Button Events and Exceptions

    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            if(ae.getSource() == b1)
            {
                Node temp = create(t1.getText());
                p1 = temp;
                ta.append("First Polynomial Created!\n");
            }

            else if(ae.getSource() == b2)
            {
                Node temp = create(t2.getText());
                p2 = temp;
                ta.append("Second Polynomial Created!\n");
            }

            else if(ae.getSource() == b3)
            {
                ta.append("First Polynomial: ");
                display(p1);
            }

            else if(ae.getSource() == b4)
            {
                ta.append("Second Polynomial: ");
                display(p2);
            }

            else if(ae.getSource() == b5)
            {
                if(p1 == null || p2 == null)
                    throw new IllegalArgumentException(
                        "Create both polynomials first!"
                    );

                result = add(p1, p2);

                ta.append("Addition Result: ");
                display(result);
            }

            else if(ae.getSource() == b6)
            {
                ta.setText("");
            }
        }

        catch(NumberFormatException ex)
        {
            ta.append("Error: Enter numeric values only!\n");
        }

        catch(IllegalArgumentException ex)
        {
            ta.append("Error: " + ex.getMessage() + "\n");
        }

        catch(NullPointerException ex)
        {
            ta.append("Error: Polynomial is not initialized!\n");
        }

        catch(Exception ex)
        {
            ta.append("Unexpected error occurred!\n");
        }
    }

    public static void main(String args[])
    {
        new PolynomialAWT();
    }
}